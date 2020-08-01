package com.yogadimas.ymovie.model.tv;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tb_tv")
public class ResultsTv implements Parcelable {

    public static final Creator<ResultsTv> CREATOR = new Creator<ResultsTv>() {
        @Override
        public ResultsTv createFromParcel(Parcel source) {
            return new ResultsTv(source);
        }

        @Override
        public ResultsTv[] newArray(int size) {
            return new ResultsTv[size];
        }
    };
    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    private String firstAirDate;
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    private String originalLanguage;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdropPath;
    @ColumnInfo(name = "original_name")
    @SerializedName("original_name")
    private String originalName;
    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    private double popularity;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private double voteAverage;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @NonNull
    private int id;
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private int voteCount;

    public ResultsTv() {
    }

    protected ResultsTv(Parcel in) {
        this.firstAirDate = in.readString();
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.originalName = in.readString();
        this.popularity = in.readDouble();
        this.voteAverage = in.readDouble();
        this.name = in.readString();
        this.id = in.readInt();
        this.voteCount = in.readInt();
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstAirDate);
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.originalName);
        dest.writeDouble(this.popularity);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeInt(this.voteCount);
    }
}
