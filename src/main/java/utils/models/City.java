package utils.models;
import com.fasterxml.jackson.annotation.JsonProperty;

public class City {
    @JsonProperty("insee_code")
    private String inseeCode;

    @JsonProperty("city_code")
    private String cityCode;

    @JsonProperty("zip_code")
    private String zipCode;

    private String label;
    private String latitude;
    private String longitude;

    @JsonProperty("department_name")
    private String departmentName;

    @JsonProperty("department_number")
    private String departmentNumber;

    @JsonProperty("region_name")
    private String regionName;

    @JsonProperty("region_geojson_name")
    private String regionGeojsonName;

    // Getters and Setters
    public String getInseeCode() {
        return inseeCode;
    }

    public void setInseeCode(String inseeCode) {
        this.inseeCode = inseeCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionGeojsonName() {
        return regionGeojsonName;
    }

    public void setRegionGeojsonName(String regionGeojsonName) {
        this.regionGeojsonName = regionGeojsonName;
    }

    @Override
    public String toString() {
        return "City{" +
                "inseeCode='" + inseeCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", label='" + label + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", departmentNumber='" + departmentNumber + '\'' +
                ", regionName='" + regionName + '\'' +
                ", regionGeojsonName='" + regionGeojsonName + '\'' +
                '}';
    }
}
