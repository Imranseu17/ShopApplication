package com.example.restaurant.domain.enumClass;

public enum SearchByKeywordEnum {
    SearchByKeyword_SUCCESS(1,201),
    SearchByKeyword_FAILED(2,501),
    ERROR_CODE_100(8,100),
    ERROR_CODE_406(9,406),
    SERVER_ERROR(3,999);


    private int key;
    private int code;

    SearchByKeywordEnum(int key, int code) {
        this.key = key;
        this.code = code;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static SearchByKeywordEnum getByCode(int code){
        for(SearchByKeywordEnum rs : SearchByKeywordEnum.values()){
            if(rs.code == code) return rs;
        }

        return null;


    }
}
