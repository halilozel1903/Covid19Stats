package com.halil.ozel.covid19stats.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.halil.ozel.covid19stats.R;
import com.halil.ozel.covid19stats.api.CoronaApi;
import com.halil.ozel.covid19stats.api.CoronaService;
import com.halil.ozel.covid19stats.data.CountriesResponse;
import com.halil.ozel.covid19stats.ui.activity.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder> implements Filterable {

    private List<CountriesResponse> countriesList;
    private List<CountriesResponse> countriesListed;
    private Context context;


    public void setCountryList(Context context, final List<CountriesResponse> countriesList) {
        this.context = context;
        if (this.countriesList == null) {
            this.countriesList = countriesList;
            this.countriesListed = countriesList;
            notifyItemChanged(0, countriesListed.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return CountryAdapter.this.countriesList.size();
                }

                @Override
                public int getNewListSize() {
                    return countriesList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return CountryAdapter.this.countriesList.get(oldItemPosition).getCountry() == countriesList.get(newItemPosition).getCountry();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    CountriesResponse newMovie = CountryAdapter.this.countriesList.get(oldItemPosition);

                    CountriesResponse oldMovie = countriesList.get(newItemPosition);

                    return newMovie.getCountry() == oldMovie.getCountry();
                }
            });
            this.countriesList = countriesList;
            this.countriesListed = countriesList;
            result.dispatchUpdatesTo(this);
        }
    }


    @Override
    public CountryHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_row, parent, false);
        return new CountryHolder(view);
    }


    @Override
    public void onBindViewHolder(CountryHolder holder, final int position) {
        holder.countryTitle.setText("Total Death : " + countriesListed.get(position).getDeaths());
        holder.countryName.setText(countriesListed.get(position).getCountry());
        Picasso.with(context).load(countriesListed.get(position).getCountryInfo().getFlag()).into(holder.image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CoronaService coronaService =
                        CoronaApi.getRetrofitInstance().create(CoronaService.class);


                Call<CountriesResponse> call = coronaService.getCountryInfo(countriesListed.get(position).getCountry());
                call.enqueue(new Callback<CountriesResponse>() {
                    @Override
                    public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> response) {


                        //System.out.println("response size : "+responseList.size());


                        Intent intent = new Intent(view.getContext(), DetailActivity.class);

                        if (response.body() != null) {
                            intent.putExtra("country", response.body().getCountry());
                            intent.putExtra("todayCase", response.body().getTodayCases());
                            intent.putExtra("todayDeath", response.body().getTodayDeaths());
                            intent.putExtra("flag", response.body().getCountryInfo().getFlag());
                            intent.putExtra("cases", response.body().getCases());
                            intent.putExtra("deaths", response.body().getDeaths());
                            intent.putExtra("tests", response.body().getTests());
                            intent.putExtra("recovered", response.body().getRecovered());
                        }


                        view.getContext().startActivity(intent);


                    }

                    @Override
                    public void onFailure(Call<CountriesResponse> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });


            }
        });
    }


    @Override
    public int getItemCount() {

        if (countriesList != null) {
            return countriesListed.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    countriesListed = countriesList;
                } else {
                    List<CountriesResponse> filteredList = new ArrayList<>();
                    for (CountriesResponse movie : countriesList) {
                        if (movie.getCountry().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                    countriesListed = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = countriesListed;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                countriesListed = (ArrayList<CountriesResponse>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }


    public class CountryHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView countryTitle;
        TextView countryName;
        ImageView image;


        public CountryHolder(View v) {
            super(v);
            cardView = v.findViewById(R.id.cvCountry);
            countryTitle = v.findViewById(R.id.tvCountryDeath);
            countryName = v.findViewById(R.id.tvCountryName);
            image = v.findViewById(R.id.ivCountryPoster);
        }
    }


}