package com.adverto.dejonghe.common.implementations;

import com.adverto.dejonghe.common.entities.WorkOrder.Team;
import com.adverto.dejonghe.common.entities.WorkOrder.TeamSummary;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.Collections;
import java.util.List;

public class TeamSummaryImplementation implements JRDataSource {

    private final List<TeamSummary> summaryTeams;
    private int index = -1;

    public TeamSummaryImplementation(List<TeamSummary> summaryTeams) {
        this.summaryTeams = summaryTeams != null
                ? summaryTeams
                : Collections.emptyList();
    }

    @Override
    public boolean next() throws JRException {
        index++;
        return index < summaryTeams.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {

        if (index < 0 || index >= summaryTeams.size()) {
            return null;
        }

        TeamSummary team = summaryTeams.get(index);

        switch (jrField.getName()) {

            case "Technicians":
                return team.getTechnicians();

            case "Vihicle":
                return team.getVihicle() != null
                        && !team.getVihicle().isEmpty()
                        ? "  " + team.getVihicle()
                        : null;

            case "RoadTunnelTax":
                return team.getRoadTunnelTax();

            case "Workhours":
                return team.getWorkHours();

            case "Comment":
                return team.getComment();

            case "date":
                return team.getDate();

            case "location":
                return team.getLocation();

            default:
                return null;
        }
    }
}
