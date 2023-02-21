import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.http.HttpClient;

public class Main {
    // Ссылка на которую будем делать запрос
    public static final String URL = "https://api.nasa.gov/planetary/apod?api_key=9iVn7vDgpIgceULdHDn86d5cxCEQ2lDaMl5Xrmvm";

    public static final ObjectMapper mapper = new ObjectMapper(); // сущность преобразующая JSON в сущность в нашем случае в сущьность NASA.

    public static void main(String[] args) throws IOException {
        // настраивает HTTP клиеент, который будет отправлять запросы
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        // отправляем запрос и получаем ответ
        CloseableHttpResponse response = httpClient.execute(new HttpGet(URL));

        // преобразуем обьект в Java обьект
        NasaObject nasaObject = mapper.readValue(response.getEntity().getContent(), NasaObject.class);
        System.out.println(nasaObject);
        // Отправляем и получаем запрос с нашей картинкой
        CloseableHttpResponse pictureResponse = httpClient.execute(new HttpGet(nasaObject.getUrl()));

        String[] arr = nasaObject.getUrl().split("/");
        String fileName = arr[arr.length - 1];

        HttpEntity entity = pictureResponse.getEntity();
        if(entity != null){
            // сохраняем в файл
            FileOutputStream fos = new FileOutputStream(fileName);
            entity.writeTo(fos);
            fos.close();
        }
    }
}