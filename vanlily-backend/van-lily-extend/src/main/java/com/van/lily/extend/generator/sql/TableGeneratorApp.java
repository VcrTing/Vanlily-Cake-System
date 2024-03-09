package com.van.lily.extend.generator.sql;

import com.baomidou.mybatisplus.annotation.TableName;
import com.van.lily.commons.core.annotations.TableJsonField;
import com.van.lily.model.order.entity.Delivery;
import com.van.lily.model.order.entity.Order;
import com.van.lily.model.order.entity.remind.OrderRemind;
import com.van.lily.model.product.entity.Cake;
import com.van.lily.model.system.entity.Customer;
import com.van.lily.model.system.entity.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TableGeneratorApp {

    final static String[] dbTypes = { "INNODB", "MYISAM" };
    static List<Class> classes = Arrays.asList(
            // Order.class
            // Delivery.class
            // Customer.class
            // Cake.class
            // User.class
            // OrderRemind.class
    );

    final static String id = "id";
    final static String logicTrash = "live";
    final static String logicTrashValue = "1";

    static Class<TableJsonField> jsonFieldAnnotation = TableJsonField.class;

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        classes.forEach(entity -> {
            Optional<String> ops = Optional.ofNullable((TableName) entity.getAnnotation(TableName.class)).map(TableName::value);
            // 表格名称
            String tableName = ops.orElse(QSqlGeneratorUtil.DEF_TABLE_NAME);
            // 表格引擎
            String dbTypeName = dbTypes[1];
            // 是否自增 ID
            boolean idAutoIncrement = false;

            // 插入 SQL
            sb.append(QSqlGeneratorUtil.generatorSql(
                    entity,
                    tableName,
                    dbTypeName,
                    id,
                    idAutoIncrement,
                    logicTrash,
                    logicTrashValue,
                    jsonFieldAnnotation));
            sb.append("\n");
        });

        System.out.println(sb);
    }
}
