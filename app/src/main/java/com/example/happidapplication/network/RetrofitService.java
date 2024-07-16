//import com.example.happidapplication.network.APIInterface;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class RetrofitService {
//    private static final String BASE_URL = "https://api.themoviedb.org";
//    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//    private static final Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(httpClient.build())
//            .build();
//
//    public static APIInterface getInterface() {
//        return retrofit.create(APIInterface.class);
//    }
//}