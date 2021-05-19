package com.interstellar.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;

public  class RtoCityMain implements Parcelable {
        /**
         * id : 44
         * city_id : 5
         * series_no : RJ14
         * rto_location : Jaipur
         */

        private int id;
        private int city_id;
        private String series_no;
        private String rto_location;

    protected RtoCityMain(Parcel in) {
        id = in.readInt();
        city_id = in.readInt();
        series_no = in.readString();
        rto_location = in.readString();
    }

    public static final Creator<RtoCityMain> CREATOR = new Creator<RtoCityMain>() {
        @Override
        public RtoCityMain createFromParcel(Parcel in) {
            return new RtoCityMain(in);
        }

        @Override
        public RtoCityMain[] newArray(int size) {
            return new RtoCityMain[size];
        }
    };

    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getSeries_no() {
            return series_no;
        }

        public void setSeries_no(String series_no) {
            this.series_no = series_no;
        }

        public String getRto_location() {
            return rto_location;
        }

        public void setRto_location(String rto_location) {
            this.rto_location = rto_location;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(city_id);
        dest.writeString(series_no);
        dest.writeString(rto_location);
    }
}