package com.adverto.dejonghe.common.implementations;

import com.adverto.dejonghe.common.entities.WorkOrder.Team;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.List;

public class TeamImplementation implements JRDataSource {


    private int lastFiledAdded;
    List<Team>teams;

    public TeamImplementation(List<Team>teams) {
        this.teams = teams;
        lastFiledAdded = teams.size() ;
    }

    @Override
    public boolean next() throws JRException {
        if(lastFiledAdded > 0 ){
            lastFiledAdded --;
            return true;
        }
        return false;
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        if (jrField.getName().equals("Technicians")) {
            try{
                if((teams.get(lastFiledAdded).getTechnicians() != null)){
                    return teams.get(lastFiledAdded).getTechnicians();
                }
                else{
                    return null;
                }
            }
            catch (Exception e){
                return null;
            }

        }
        else if (jrField.getName().equals("Vihicle")) {
            if((teams.get(lastFiledAdded).getVihicle() != null) && ((teams.get(lastFiledAdded).getVihicle().length() > 0))){
                return "  " + teams.get(lastFiledAdded).getVihicle();
            }
            return null;
        }
        else if (jrField.getName().equals("RoadTunnelTax")) {
            if(teams.get(lastFiledAdded).getRoadTunnelTax() != null){
                return teams.get(lastFiledAdded).getRoadTunnelTax();
            }
            return null;
        }
        else if (jrField.getName().equals("Workhours")) {
            if((teams.get(lastFiledAdded).getWorkHours() != null) && (teams.get(lastFiledAdded).getWorkHours().size() > 0)){
                return teams.get(lastFiledAdded).getWorkHours();
            }
            return null;
        }
        return null;
    }
}
