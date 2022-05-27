package Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

public class list_check implements Parcelable {
    public list_check(int id, String content, Boolean done, int assign){
        this.content = content;
        this.link = -1;
        this.done = done;
        this.id = id;
        this.assign = assign;
    }
    private String content;
    private int link;
    private Boolean done;
    private int id;
    private int assign;

    protected list_check(Parcel in) {
        content = in.readString();
        link = in.readInt();
        byte tmpDone = in.readByte();
        done = tmpDone == 0 ? null : tmpDone == 1;
        id = in.readInt();
        assign = in.readInt();
    }

    public static final Creator<list_check> CREATOR = new Creator<list_check>() {
        @Override
        public list_check createFromParcel(Parcel in) {
            return new list_check(in);
        }

        @Override
        public list_check[] newArray(int size) {
            return new list_check[size];
        }
    };

    public void setAssign(int assign) {
        this.assign = assign;
    }

    public int getAssign() {
        return assign;
    }

    public int getId() {
        return id;
    }

    public String getContent() { return content; }
    public int getLink() {
        return link;
    }
    public Boolean getDone() {
        return done;
    }
    public void setContent(String content) { this.content = content; }
    public void setLink(int link) {
        this.link = link;
    }
    public void setDone(Boolean done) {
        this.done = done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAll(String input){
        if(input.indexOf("list_check(") != -1){
            String[] list = input.split("list_check\\(", 1)[1].split("\\)")[0].split(",");
            for (String s: list) Log.e("check", s);
            id = Integer.parseInt(list[0]);
            content = list[1];
            done = Boolean.valueOf(list[2]);
            assign = Integer.parseInt(list[3]);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(content);
        parcel.writeInt(link);
        parcel.writeByte((byte) (done == null ? 0 : done ? 1 : 2));
        parcel.writeInt(id);
        parcel.writeInt(assign);
    }
}
