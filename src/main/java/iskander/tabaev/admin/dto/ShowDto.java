package iskander.tabaev.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowDto {
    private Integer showId;

    private String name;

    private Integer AgeRestriction;

    private String City;

    public ShowDto(int showId, String name, int ageRestriction, String city) {
        this.showId = showId;
        this.name = name;
        AgeRestriction = ageRestriction;
        City = city;
    }

    public ShowDto() {
    }

    public String getShowIdText() {
        if (this.showId==null){
            return "";
        }
        return String.valueOf(showId);
    }

    public void setShowIdText(String showId) {
        this.showId = Integer.parseInt(showId);
    }



    public String getAgeRestrictionText() {
        if (this.AgeRestriction==null){
            return "";
        }
        return String.valueOf(AgeRestriction);
    }

    public void setAgeRestrictionText(String ageRestriction) {
        AgeRestriction = Integer.parseInt(ageRestriction);
    }
}
