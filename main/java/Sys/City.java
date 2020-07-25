package Sys;

//gmt 값->서울 9 ,파리1, 런던0,  시드니10,LA -7 ,뉴욕 -4

public class City {
    private int GMT;
    private String CityName;

    public City(int GMT, String CityName) {
        this.GMT=GMT;
        this.CityName=CityName;
    }

    public int getGMT() {
        return GMT;
    }
    public void setGMT(int gMT) {
        GMT = gMT;
    }
    public String getCityName() {
        return CityName;
    }
    public void setCityName(String cityName) {
        CityName = cityName;
    }


}