import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class OrtuDns {

 // Fungsi untuk mendapatkan token API key
 public static String getApiKey(String username, String password) {
     HttpURLConnection connection;
     try {
         // URL endpoint untuk mendapatkan token API key
         String loginUrl = "https://akademik.stmik-im.ac.id/api/login";

         // Membuat objek URL
         URL url = new URL(loginUrl);

         // Membuat objek HttpURLConnection
         connection = (HttpURLConnection) url.openConnection();

         // Menentukan metode request sebagai POST
         connection.setRequestMethod("POST");

         // Mengaktifkan output stream untuk mengirim data
         connection.setDoOutput(true);

         // Menyiapkan data login sebagai form-urlencoded
         String postData = "username=" + username + "&password=" + password;
         try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())){
         wr.write(postData.getBytes(StandardCharsets.UTF_8));
     } catch (ProtocolException ex) {
             throw new RuntimeException(ex);
         }

     // Membaca response dari server
     try (BufferedReader in = new BufferedReader(new
             InputStreamReader(connection.getInputStream()))) {
         String line;
         StringBuilder response = new StringBuilder();
         while ((line = in.readLine()) != null) {
             response.append(line);
         }
         // Mengembalikan token API key
         // return response.toString();
         // Mengambil nilai "JWT" dari respons JSON secara manual
         String jsonResponse = response.toString();
         int startIndex = jsonResponse.indexOf("\"JWT\":\"") + 7;
         int endIndex = jsonResponse.indexOf("\"}", startIndex);
         return jsonResponse.substring(startIndex, endIndex);


     }
 } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
 }

    public static void executeGetRequest(String apiKey) {
        try {
         // URL endpoint untuk request GET
         String apiUrl = "https://akademik.stmik-im.ac.id/api/list/WaliIps";

         // Membuat objek URL
         URL url = new URL(apiUrl);

         // Membuat objek HttpURLConnection
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();

         // Menentukan metode request sebagai GET
         connection.setRequestMethod("GET");

         // Menambahkan header X-Authorization dengan token API key
         connection.setRequestProperty("X-Authorization", apiKey);

         // Membaca response dari server
         try (BufferedReader in = new BufferedReader(new
                InputStreamReader(connection.getInputStream()))) {
             String line;
             StringBuilder response = new StringBuilder();
             while ((line = in.readLine()) != null) {
                 response.append(line);
                 }

             // Menampilkan response dari request GET
             System.out.println("Response from GET request: " + response.toString());
             }

         // Menutup koneksi
         connection.disconnect();
         } catch (Exception e) {
         e.printStackTrace();
         }
     }

         public static void main(String[] args) {
     try {
         // Data login (ganti sesuai kebutuhan)
         String username = "362201010";
         String password = "100401";
         // Mendapatkan token API key dengan fungsi baru
         String apiKey = getApiKey(username, password);

          // Menampilkan token API key
         System.out.println("Token API Key: " + apiKey);

          // Melanjutkan dengan request GET menggunakan apiKey
          executeGetRequest(apiKey);
          } catch (Exception e) {
          e.printStackTrace();
          }
              }
 }