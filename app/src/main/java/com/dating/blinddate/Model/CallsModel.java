package com.dating.blinddate.Model;

import android.widget.ImageView;

public class CallsModel {
    public String getProfilpic() {
        return profilpic;
    }

    public void setProfilpic(String profilpic) {
        this.profilpic = profilpic;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCallVia() {
        return callVia;
    }

    public void setCallVia(String callVia) {
        this.callVia = callVia;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public CallsModel(String profilpic, String name, String time, String callVia, String callType) {
        this.profilpic = profilpic;
        Name = name;
        Time = time;
        this.callVia = callVia;
        this.callType = callType;
    }

    String profilpic, Name,Time,callVia,callType;
}
