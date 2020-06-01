package com.kttss.counter.util;

public class SqlQuery {
    public static final String DATABASE_NAME="COUNTER_DB";

    public static final int DATABASE_VERSION=1;

    public static final String COUNTER_TABLE_NAME="COUNTER";
    public static final String CONFIGURATION_TABLE_NAME="CONFIGURATION";

    //COUNTER
    public static final String COUNTER_ID="ID";
    public static final String COUNTER_NAME="NAME";
    public static final String COUNTER_DATE="DATE";
    public static final String COUNTER_LAP="LAP";
    public static final String COUNTER_VALUE="VALUE";

    //CONFIGURATION
    public static final String CONFIGURATION_ID="ID";
    public static final String CONFIGURATION_KEY="KEY";
    public static final String CONFIGURATION_VALUE="VALUE";


    //COUNTER
    public static final String COUNTER_TABLE_CREATE_STATEMENT="CREATE TABLE `"+COUNTER_TABLE_NAME+"` (" +
            "`"+COUNTER_ID+"` INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "`"+COUNTER_NAME+"` TEXT, " +
            "`"+COUNTER_DATE+"` TEXT, "+
            "`"+COUNTER_LAP+"` REAL, " +
            "`"+COUNTER_VALUE+"` REAL " +
            ");";

    //CONFIGURATION
    public static  final String CONFIGURATION_CREATE_STATEMENT="CREATE TABLE `"
            +CONFIGURATION_TABLE_NAME+"` ( `"
            +CONFIGURATION_ID+"` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `"
            +CONFIGURATION_KEY+"` TEXT, `"
            +CONFIGURATION_VALUE+"` TEXT );";

    //COUNTER
    public static String READ_ALL_COUNTER="SELECT * FROM "+COUNTER_TABLE_NAME+";";

    public static String LOAD_COUNTER(int id){
        return "SELECT * FROM "+COUNTER_TABLE_NAME+" WHERE "+COUNTER_ID+"="+id+";";
    }
    public static String LOAD_COUNTER(String key){
        return "SELECT * FROM "+COUNTER_TABLE_NAME+" WHERE "+COUNTER_NAME+"="+key+";";
    }

    //CONFIGURATION
    public static String LOAD_CONFIG(int id){
        return "SELECT * FROM "+CONFIGURATION_TABLE_NAME+" WHERE "+CONFIGURATION_ID+"="+id+";";
    }
    public static String LOAD_CONFIG(String key){
        return "SELECT * FROM "+CONFIGURATION_TABLE_NAME+" WHERE "+CONFIGURATION_KEY+"='"+key+"';";
    }

}
