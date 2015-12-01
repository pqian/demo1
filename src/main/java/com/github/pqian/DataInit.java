package com.github.pqian;

import java.io.IOException;
import java.math.BigDecimal;

import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.pqian.entity.Product;
import com.github.pqian.repository.ProductRepository;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

@Component
@Slf4j
public class DataInit {

	@Autowired
	private ProductRepository productRepository;
	
	@Scheduled(fixedDelay = 3600000)
	public void warmDataUp() {
		log.info("Warm product data up for search...");

		CloseableHttpClient httpClient = null;
		HttpGet req = null;
		HttpResponse resp = null;
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    
//		    SSLContextBuilder builder = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//				@Override
//				public boolean isTrusted(
//						java.security.cert.X509Certificate[] chain,
//						String authType)
//						throws java.security.cert.CertificateException {
//					return true;
//				}
//            });
		    
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
		    httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setUserAgent("Mozilla/5.0 Firefox/26.0").build();
		    
			// req = new HttpGet("https://www.zalora.com.my/mobile­api/women/clothing?maxitems=1&page=1");
			req = new HttpGet("https://www.zalora.com.my/mobile­api/women/clothing?maxitems=60&page=1");
			resp = httpClient.execute(req);

			log.info("Response {}", resp.getStatusLine());
			if (resp.getStatusLine().getStatusCode() == 200) {
				ReadContext ctx = JsonPath.parse(EntityUtils.toString(resp.getEntity()));
				Boolean success = ctx.read("$.success");
				if (success) {
					int size = ctx.read("$.metadata.results.length()");
					for (int i = 0; i<size; i++) {
						Long id = ctx.read("$.metadata.results["+ i +"].id");
						String name = ctx.read("$.metadata.results["+ i +"].data.name");
						String brand = ctx.read("$.metadata.results["+ i +"].data.brand");
						BigDecimal price = ctx.read("$.metadata.results["+ i +"].data.price");
						String image = ctx.read("$.metadata.results["+ i +"].images[?(@.default)].path");
						Product p = new Product();
						p.setId(id);
						p.setName(name);
						p.setBrand(brand);
						p.setPrice(price);
						p.setImage(image);
						productRepository.save(p);
						log.info("Saved product: {}", p);
					}
				} else {
					log.error("Failed to retrieve data");
				}
			} else {
				log.error("Product cannot be prepared because {}", resp);
			}
		} catch (final Exception e) {
			log.error("Product cannot be prepared", e);
		} finally {
			releaseConnection(req, resp);
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					log.warn("HttpClient cannot be closed", e);
				}
			}
		}
	}

	private void releaseConnection(final HttpRequestBase req,
			final HttpResponse resp) {
		if (req != null && !req.isAborted()) {
			req.abort();
		}
		if (resp != null) {
			try {
				EntityUtils.consume(resp.getEntity());
			} catch (final IOException e) {
				log.warn("Failed to release connection", e);
			}
		}
	}
}
