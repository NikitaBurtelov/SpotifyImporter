package spotify;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupRequest {

    public static String getToken(String code, String clientId, String clientSecret) {
        try {
            String url = "curl -H \"Content-Type: application/x-www-form-urlencoded\" -d \"grant_type=authorization_code&client_id=c700db30083545a6a05352d0304a0597&client_secret=3938d992c7a7492ebd98e2bb45b2d259&code=AQAoqH34YljVcU6lcfwdnQW-Vm6z9j7wta3jSwti2SvwR-xYSLQ02F9V09wNFK1mT7vj0ZrPauMZ5hOrbh-uG3jNZ8Tqs7zZNduGVhdUKJse4K-7OnRmyKeHrqxlKaE4oSNB3c6oVm4KX2jukEOOhwIpAuMcxRpRt52B3eyy99cJPw&redirect_uri=http%3A%2F%2Flocalhost%3A8888%2Fcallback\" -X POST https://accounts.spotify.com/api/token";

            Document doc = Jsoup.connect("https://accounts.spotify.com/api/token")
                    .header("Accept-Language", "en")
                    .header("Content-Type","application/x-www-form-urlencoded;charset=UTF-8")
                    .data("grant_type","authorization_code")
                    .data("client_id",clientId)
                    .data("client_secret",clientSecret)
                    .data("code", code)
                    .data("redirect_uri", "http://localhost:8888/callback")
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9"+ ",*/*;q=0.8")
                    .post();

            return doc.text();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        return new Exception().getMessage();
    }
}
