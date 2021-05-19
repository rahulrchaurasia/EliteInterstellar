package com.interstellar.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CityMainEntity implements Parcelable {
    /**
     * city_id : 2
     * cityname : Hyderabad
     * RTOList : [{"id":106,"city_id":2,"series_no":"AP10","rto_location":"Hyderabad"},{"id":107,"city_id":2,"series_no":"TS10","rto_location":"Hyderabad"},{"id":108,"city_id":2,"series_no":"AP09","rto_location":"Hyderabad"},{"id":109,"city_id":2,"series_no":"TS09","rto_location":"Hyderabad"},{"id":110,"city_id":2,"series_no":"AP11","rto_location":"Hyderabad"},{"id":111,"city_id":2,"series_no":"TS11","rto_location":"Hyderabad"},{"id":112,"city_id":2,"series_no":"AP12","rto_location":"Hyderabad"},{"id":113,"city_id":2,"series_no":"TS12","rto_location":"Hyderabad"},{"id":114,"city_id":2,"series_no":"AP13","rto_location":"Hyderabad"},{"id":115,"city_id":2,"series_no":"TS13","rto_location":"Hyderabad"},{"id":116,"city_id":2,"series_no":"TS14","rto_location":"Hyderabad"},{"id":121,"city_id":2,"series_no":"AP25","rto_location":"Hyderabad"},{"id":122,"city_id":2,"series_no":"AP26","rto_location":"Hyderabad"},{"id":123,"city_id":2,"series_no":"AP27","rto_location":"Hyderabad"},{"id":124,"city_id":2,"series_no":"AP28","rto_location":"Hyderabad"}]
     */

    private int city_id;
    private String cityname;
    private List<RtoCityMain> RTOList;

    protected CityMainEntity(Parcel in) {
        city_id = in.readInt();
        cityname = in.readString();
        RTOList = in.createTypedArrayList(RtoCityMain.CREATOR);
    }

    public static final Creator<CityMainEntity> CREATOR = new Creator<CityMainEntity>() {
        @Override
        public CityMainEntity createFromParcel(Parcel in) {
            return new CityMainEntity(in);
        }

        @Override
        public CityMainEntity[] newArray(int size) {
            return new CityMainEntity[size];
        }
    };

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public List<RtoCityMain> getRTOList() {
        return RTOList;
    }

    public void setRTOList(List<RtoCityMain> RTOList) {
        this.RTOList = RTOList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(city_id);
        dest.writeString(cityname);
        dest.writeTypedList(RTOList);
    }
}
