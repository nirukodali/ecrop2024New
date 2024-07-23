package com.ecrops.dto.crop.response;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
//import org.apache.hc.client5.http.classic.methods.HttpPost;

import com.itextpdf.text.log.LoggerFactory;
import ch.qos.logback.classic.Logger;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

@Component
public class SendSMSService {

    private static final com.itextpdf.text.log.Logger logger = LoggerFactory.getLogger(SendSMSService.class);

    public void sendNicSms(String mobileNumber, String msg) throws Exception {
        String uname = "karshak.sms";
        String pass = "P0@fN3$vC0";
        String send = "APGOVT";
        String pmsgType = "UC";

        String emsg = URLEncoder.encode(msg, "UTF-8");
        String httpsUrl = "https://smsgw.sms.gov.in/failsafe/HttpLink?username=" + uname + "&pin=" + pass +
                          "&message=" + emsg + "&mnumber=" + mobileNumber + "&signature=" + send + "msgType=" + pmsgType;

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = getTrustManagers();
            sslContext.init(null, trustManagers, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            URL url = new URL(httpsUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            logContent(connection);

        } catch (MalformedURLException e) {
            logger.error("MalformedURLException: {}");
            throw e;
        } catch (IOException e) {
            logger.error("IOException: {}");
            throw e;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            logger.error("SSL Exception: {}");
            throw e;
        }
    }

    private TrustManager[] getTrustManagers() {
        return new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String t) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String t) {
                }
            }
        };
    }

    private void logContent(HttpsURLConnection connection) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String input;
            while ((input = br.readLine()) != null) {
                logger.debug(input);
            }
        }
    }

    private static String MD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] md5 = md.digest(text.getBytes("iso-8859-1"));
        return convertedToHex(md5);
    }

    private static String convertedToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfOfByte = (b >>> 4) & 0x0F;
            int twoHalfBytes = 0;
            do {
                if ((0 <= halfOfByte) && (halfOfByte <= 9)) {
                    buf.append((char) ('0' + halfOfByte));
                } else {
                    buf.append((char) ('a' + (halfOfByte - 10)));
                }
                halfOfByte = b & 0x0F;
            } while (twoHalfBytes++ < 1);
        }
        return buf.toString();
    }

    protected String hashGenerator(String userName, String senderId, String content, String secureKey) {
        String finalString = userName.trim() + senderId.trim() + content.trim() + secureKey.trim();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] byteData = md.digest(finalString.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : byteData) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException: {}");
            return null;
        }
    }

    public String sendNicSmsWithTemplate(String mobileNumber, String msg, String templateId) throws Exception {
        String uname = "eseedsap.otp";
        String pass = "1tfph2jp";
        String send = "GOVTAP";
        String entityId = "1105160387255698489";

        String emsg = URLEncoder.encode(msg, "UTF-8");
        String httpsUrl = "https://smsgw.sms.gov.in/failsafe/MLink?username=" + uname + "&pin=" + pass +
                          "&message=" + emsg + "&mnumber=91" + mobileNumber + "&signature=" + send +
                          "&dlt_entity_id=" + entityId + "&dlt_template_id=" + templateId;

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = getTrustManagers();
            sslContext.init(null, trustManagers, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            URL url = new URL(httpsUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            logContent(connection);
            int status = connection.getResponseCode();
            String statMsg = connection.getResponseMessage();
            return status + "@" + statMsg;
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException: {}");
            return e.getMessage();
        } catch (IOException e) {
            logger.error("IOException: {}");
            return e.getMessage();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            logger.error("SSL Exception: {}");
            return e.getMessage();
        }
    }

//    public String sendCDACSMShttps(String username, String password, String message, String senderId, String mobileNumber, String secureKey, String templateid) {
//        String responseString = "";
//        try {
//            HttpPost post = new HttpPost("https://msdgweb.mgov.gov.in/esms/sendsmsrequest");
//            String encryptedPassword = MD5(password);
//            String generatedHashKey = hashGenerator(username, senderId, message, secureKey);
//
//            post.addHeader("Content-Type", "application/x-www-form-urlencoded");
//            post.addHeader("Accept", "text/plain");
//
//            List<NameValuePair> nameValuePairs = List.of(
//                    new BasicNameValuePair("mobileno", mobileNumber),
//                    new BasicNameValuePair("senderid", senderId),
//                    new BasicNameValuePair("content", message),
//                    new BasicNameValuePair("smsservicetype", "singlemsg"),
//                    new BasicNameValuePair("username", username),
//                    new BasicNameValuePair("password", encryptedPassword),
//                    new BasicNameValuePair("key", generatedHashKey),
//                    new BasicNameValuePair("templateid", templateid)
//            );
//
//            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//                HttpResponse response = (HttpResponse) httpClient.execute((HttpUriRequest) post);
//                HttpEntity entity = ((BasicClassicHttpRequest) response).getEntity();
//                if (entity != null) {
//                    responseString = EntityUtils.toString((HttpEntity) entity);
//                }
//            }
//            logger.debug("CDACSMS Response: {}");
//        } catch (NoSuchAlgorithmException | IOException e) {
//            logger.error("Exception: {}");
//            responseString = e.getMessage();
//        }
//        return responseString;
//    }
//
    public String sendCDACSMShttpsUnicodeDLT(String message, String mobileNumber, String templateid) {
        String responseString = "";
        String username = "APGOVT-AGRI";
        String password = "Agri@123456";
        String senderId = "GOVTAP";
        String secureKey = "e383e93d-6946-48cd-91cb-2f87580e912b";

        try {
            SSLContext sslContext = null;
			try {
				sslContext = SSLContextBuilder.create()
				        .loadTrustMaterial(null, (certificate, authType) -> true)
				        .build();
			} catch (KeyStoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            try (CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build()) {
                HttpPost post = new HttpPost("https://msdgweb.mgov.gov.in/esms/sendsmsrequestDLT");

                StringBuilder finalMessage = new StringBuilder();
                for (int i = 0; i < message.length(); i++) {
                    char ch = message.charAt(i);
                    finalMessage.append("&#").append((int) ch).append(";");
                }

                String generatedHashKey = hashGenerator(username, senderId, finalMessage.toString(), secureKey);

                List<org.apache.http.NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new org.apache.http.message.BasicNameValuePair("mobileno", mobileNumber));
                nameValuePairs.add(new org.apache.http.message.BasicNameValuePair("senderid", senderId));
                nameValuePairs.add(new org.apache.http.message.BasicNameValuePair("content", finalMessage.toString()));
                nameValuePairs.add(new org.apache.http.message.BasicNameValuePair("smsservicetype", "unicodemsg"));
                nameValuePairs.add(new org.apache.http.message.BasicNameValuePair("username", username));
                nameValuePairs.add(new org.apache.http.message.BasicNameValuePair("password", MD5(password)));
                nameValuePairs.add(new org.apache.http.message.BasicNameValuePair("key", generatedHashKey));
                nameValuePairs.add(new org.apache.http.message.BasicNameValuePair("templateid", templateid));

                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                try (CloseableHttpResponse response = client.execute((HttpUriRequest) post);
                     BufferedReader bf = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                    String line;
                    while ((line = bf.readLine()) != null) {
                        responseString += line;
                    }
                } catch (IOException e) {
                    logger.error("IOException while sending SMS: {}");
                    responseString = e.getMessage();
                }
            }
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            logger.error("SSL context initialization failed: {}");
            responseString = e.getMessage();
        } catch (IOException e) {
            logger.error("IOException during HTTP client setup: {}");
            responseString = e.getMessage();
        }

        return responseString;
//    	return null;
    }

    

}
