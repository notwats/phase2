package project.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

// just for a stupid table

@Getter
@Setter
public class ViewReport {

    LocalDate eachDay;
    Integer viewNum = 0;
    Integer likNum = 0;


    public static ArrayList<ViewReport> sortByDay(ArrayList<LocalDate> viewlist, ArrayList<LocalDate> likelist, LocalDate creationDate) {
        ArrayList<ViewReport> ret = new ArrayList<>();
        LocalDate now = LocalDate.now();
        Integer today = now.getDayOfYear();
        System.out.println(today);
        System.out.println(creationDate.getDayOfYear());
        for (int i = creationDate.getDayOfYear(); i <= today; i++) {
            ViewReport viewReport = new ViewReport();
            for (LocalDate dd : viewlist) {
                if (dd.getDayOfYear() == i) {
                    viewReport.viewNum += 1;
                    viewReport.eachDay = dd;
                }
            }
            for (LocalDate dd : likelist) {
                if (dd.getDayOfYear() == i) {
                    viewReport.likNum += 1;
                    viewReport.eachDay = dd;
                }
            }
            if (viewReport.eachDay != null)
                ret.add(viewReport);
        }
        return ret;
    }
}
