package com.apptutti.tuttistore.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * _id : 5c2dabdb9d21226e068debf9
 * createdAt : 2019-01-03T06:29:47.895Z
 * desc : 2019-01-03
 * publishedAt : 2019-01-03T00:00:00.0Z
 * source : web
 * type : 福利
 * url : https://ws1.sinaimg.cn/large/0065oQSqly1fytdr77urlj30sg10najf.jpg
 * used : true
 * who : lijinshanmx
 */

public class MeiziResult implements Parcelable{
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public MeiziResult() {
    }

    public String get_id(){
        return _id;
    }

    public void set_id(String _id){
        this._id = _id;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getPublishedAt(){
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt){
        this.publishedAt = publishedAt;
    }

    public String getSource(){
        return source;
    }

    public void setSource(String source){
        this.source = source;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public boolean isUsed(){
        return used;
    }

    public void setUsed(boolean used){
        this.used = used;
    }

    public String getWho(){
        return who;
    }

    public void setWho(String who){
        this.who = who;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this._id);
        dest.writeString(this.createdAt);
        dest.writeString(this.desc);
        dest.writeString(this.publishedAt);
        dest.writeString(this.source);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte( (this.used ? (byte)1 : (byte)0));
        dest.writeString(this.who);
    }


    protected MeiziResult(Parcel in){
        this._id = in.readString();
        this.createdAt = in.readString();
        this.desc = in.readString();
        this.publishedAt = in.readString();
        this.source = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this.who = in.readString();
    }

    public static final Parcelable.Creator<MeiziResult> CREATOR = new Parcelable.Creator<MeiziResult>(){
        @Override
        public MeiziResult createFromParcel(Parcel in){
            return new MeiziResult(in);
        }

        @Override
        public MeiziResult[] newArray(int size){
            return new MeiziResult[size];
        }
    };


}
