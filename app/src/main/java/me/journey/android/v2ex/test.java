package me.journey.android.v2ex;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by journey on 2017/12/29.
 */

public class test {
	public void main(){
		Retrofit retrofit= new Retrofit.Builder()
				.baseUrl("https://api.github.com")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		GetAPIService service = retrofit.create(GetAPIService.class);

		Call<ArrayList<Hot>> model = service.listRepos();
		model.enqueue(new Callback<ArrayList<Hot>>() {
			@Override
			public void onResponse(Call<ArrayList<Hot>> call, Response<ArrayList<Hot>> response) {
				// Log.e("Test", response.body().getLogin());
//				System.out.print(response.body().getContent());
				for (Hot hot : response.body()) {
					
				}
			}

			@Override
			public void onFailure(Call<ArrayList<Hot>> call, Throwable t) {
				System.out.print(t.getMessage());
			}
		});
//		Call<Hot> model = service.repo("Guolei1130");
//		model.enqueue(new Callback<Hot>() {
//			@Override
//			public void onResponse(Call<Hot> call, Response<Hot> response) {
//				// Log.e("Test", response.body().getLogin());
//				System.out.print(response.body().getContent());
//			}
//
//			@Override
//			public void onFailure(Call<Hot> call, Throwable t) {
//				System.out.print(t.getMessage());
//			}
//		});
	}

}
