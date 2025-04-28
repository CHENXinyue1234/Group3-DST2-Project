package cn.edu.zju.dao;

import cn.edu.zju.bean.Drug;
import cn.edu.zju.dbutils.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DrugDao extends BaseDao {

    private static final Logger log = LoggerFactory.getLogger(DrugDao.class);

    public boolean existsById(String id) {
        return super.existsById(id, "drug");
    }

    public void saveDrug(Drug drug) {
        DBUtils.execSQL(connection -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into drug (id, name, obj_cls, biomarker, drug_url) values    (?,?,?,?,?)");
                preparedStatement.setString(1, drug.getId());
                preparedStatement.setString(2, drug.getName());
                preparedStatement.setString(3, drug.getObjCls());
                preparedStatement.setBoolean(4, drug.isBiomarker());
                preparedStatement.setString(5, drug.getDrugUrl());
                preparedStatement.execute();
            } catch (SQLException e) {
                log.info("", e);
            }
        });

    }

    public List<Drug> findAll() {
        List<Drug> drugs = new ArrayList<>();
        DBUtils.execSQL(connection -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT d.id, d.name, d.obj_cls, d.drug_url, d.biomarker, " +
                                "GROUP_CONCAT(DISTINCT dl.id) AS drug_label_ids, " +
                                "GROUP_CONCAT(DISTINCT dg.id) AS dosing_guideline_ids " +
                                "FROM drug d " +
                                "LEFT JOIN drug_label dl ON d.id = dl.drug_id " +
                                "LEFT JOIN dosing_guideline dg ON d.id = dg.drug_id " +
                                "GROUP BY d.id, d.name, d.obj_cls, d.drug_url, d.biomarker"
                );
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String objCls = resultSet.getString("obj_cls");
                    String drugUrl = resultSet.getString("drug_url");
                    boolean biomarker = resultSet.getBoolean("biomarker");
                    String drugLabelIds = resultSet.getString("drug_label_ids");
                    String dosingGuidelineIds = resultSet.getString("dosing_guideline_ids");
                    Drug drug = new Drug(id, name, biomarker, drugUrl, objCls);
                    drug.setDrugLabelId(drugLabelIds);
                    drug.setDosingGuidelineId(dosingGuidelineIds);
                    drugs.add(drug);
                }
            } catch (SQLException e) {
                log.info("", e);
            }
        });
        return drugs;
    }

    public Drug getDrugByName(String name) {
        final Drug[] result = {null};  // Use an array to capture result inside lambda

        DBUtils.execSQL(connection -> {
            try {
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT id, name, obj_cls, drug_url, biomarker FROM drug WHERE LOWER(name) = LOWER(?)"
                );
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String id = rs.getString("id");
                    String drugName = rs.getString("name");
                    String objCls = rs.getString("obj_cls");
                    String drugUrl = rs.getString("drug_url");
                    boolean biomarker = rs.getBoolean("biomarker");

                    result[0] = new Drug(id, drugName, biomarker, drugUrl, objCls);
                }

            } catch (SQLException e) {
                log.error("Error fetching drug by name", e);
            }
        });

        return result[0];  // Return the result from lambda
    }
}