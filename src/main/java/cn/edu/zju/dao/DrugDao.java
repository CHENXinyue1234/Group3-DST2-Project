package cn.edu.zju.dao;

import cn.edu.zju.bean.Drug;
import cn.edu.zju.dbutils.dbutil;
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

    public List<Drug> findAll() {
        List<Drug> drugs = new ArrayList<>();
        dbutil.execSQL(connection -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select id,name,obj_cls,drug_url,biomarker from drug");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String objCls = resultSet.getString("obj_cls");
                    String drugUrl = resultSet.getString("drug_url");
                    Boolean biomarker = resultSet.getBoolean("biomarker");
                    Drug drug = new Drug(id, name, biomarker, drugUrl, objCls);
                    drugs.add(drug);
                    log.info("Drug found: " + drug.getName());
                }
            } catch (SQLException e) {
                log.info("", e);
            }
        });
        return drugs;
    }

}