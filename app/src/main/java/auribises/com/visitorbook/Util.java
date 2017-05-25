package auribises.com.visitorbook;

import android.net.Uri;

public class Util {

    // 1. Information for Vehicle Database
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Vehicle.db";

    // Information for Vehicle Table
    public static final String TAB_NAMEVEHICLE = "Vehicle";
    public static final String COL_IDVEHICLE = "_ID";
    public static final String COL_NAMEVEHICLE = "NAME";
    public static final String COL_PHONEVEHICLE = "PHONE";
    public static final String COL_EMAILVEHICLE = "EMAIL";
    public static final String COL_GENDERVEHICLE = "GENDER";
    public static final String COL_VEHICLEVEHICLE = "VEHICLE";
    public static final String COL_VEHICLENUMBERVEHICLE = "VEHICLENUMBER";

    public static final String CREATE_TAB_QUERY = "create table Vehicle(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(20)," +
            "EMAIL varchar(256)," +
            "GENDER varchar(10)," +
            "VEHICLE varchar(256)," +
            "VEHICLENUMBER varchar(256)" +
            ")";

    // URI
    public static final Uri VEHICLE_URI = Uri.parse("content://auribises.com.vehicle.teacherprovider/"+TAB_NAMEVEHICLE);

    // URL
    public static final String INSERT_VEHICLE_PHP = "http://tajinderj.esy.es/Vehicle/insert.php";
    public static final String RETRIEVE_VEHICLE_PHP = "http://tajinderj.esy.es/Vehicle/retrieve.php";
    public static final String DELETE_VEHICLE_PHP = "http://tajinderj.esy.es/Vehicle/delete.php";
    public static final String UPDATE_VEHICLE_PHP = "http://tajinderj.esy.es/Vehicle/update.php";

    // 2. Information for Teacher Database
    public static final int DB_VERSIONTEACHER = 1;
    public static final String DB_NAMETEACHER = "Teacher.db";

    // Information for Teacher Table
    public static final String TAB_NAMETEACHER = "Teacher";
    public static final String COL_IDTEACHER = "_ID";
    public static final String COL_NAMETEACHER = "NAME";
    public static final String COL_PHONETEACHER = "PHONE";
    public static final String COL_EMAILTEACHER = "EMAIL";
    public static final String COL_GENDERTEACHER = "GENDER";
    public static final String COL_ADDRESSTEACHER = "ADDRESS";
    public static final String COL_PURPOSETEACHER = "PURPOSE";
    public static final String COL_DATETEACHER = "DATE";
    public static final String COL_TIMETEACHER = "TIME";
    public static final String COL_ROOMTEACHER = "ROOM";

    public static final String CREATE_TAB_QUERYTEACHER = "create table Teacher(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(20)," +
            "EMAIL varchar(256)," +
            "GENDER varchar(10)," +
            "ADDRESS varchar(256)," +
            "PURPOSE varchar(256)," +
            "DATE varchar(256)," +
            "TIME varchar(256)," +
            "ROOM varchar(256)" +
            ")";

    // URI
    public static final Uri TEACHER_URI = Uri.parse("content://auribises.com.vehicle.teacherprovider/"+TAB_NAMETEACHER);

    // URL
    public static final String INSERT_TEACHER_PHP = "http://tajinderj.esy.es/Teacher/insert.php";
    public static final String RETRIEVE_TEACHER_PHP = "http://tajinderj.esy.es/Teacher/retrieve.php";
    public static final String DELETE_TEACHER_PHP = "http://tajinderj.esy.es/Teacher/delete.php";
    public static final String UPDATE_TEACHER_PHP = "http://tajinderj.esy.es/Teacher/update.php";

    // 3. Information for teacherlogin Database
    public static final int DB_VERSIONTEACHERLOGIN = 1;
    public static final String DB_NAMETEACHERLOGIN = "login.db";

    // Information for login Table
    public static final String TAB_NAMETEACHERLOGIN = "login";
    public static final String COL_IDTEACHERLOGIN = "_ID";
    public static final String COL_USERNAMETEACHERLOGIN = "USERNAME";
    public static final String COL_PASSWORDTEACHERLOGIN = "PASSWORD";

    public static final String CREATE_TAB_QUERYTEACHERLOGIN = "create table login(" +
            "_ID integer primary key autoincrement," +
            "USERNAME varchar(256)," +
            "PASSWORD varchar(256)" +
            ")";

    // URI
    public static final Uri TEACHERLOGIN_URI = Uri.parse("content://auribises.com.Teacherlogin.teacherloginprovider/"+TAB_NAMETEACHERLOGIN);

    // URL
    public static final String LOGIN_PHP = "http://tajinderj.esy.es/Teacherlogin/login.php";

    // 4. Information for visitorentry Database
    public static final int DB_VERSIONVISITOR = 1;
    public static final String DB_NAMEVISITOR = "visitorentry.db";

    // Information for visitorentry Table
    public static final String TAB_NAMEVISITOR = "visitorentry";
    public static final String COL_IDVISITOR = "_ID";
    public static final String COL_NAMEVISITOR = "NAME";
    public static final String COL_PHONEVISITOR = "PHONE";
    public static final String COL_EMAILVISITOR = "EMAIL";
    public static final String COL_GENDERVISITOR = "GENDER";
    public static final String COL_ADDRESSVISITOR = "ADDRESS";
    public static final String COL_PURPOSEVISITOR = "PURPOSE";
    public static final String COL_DATEVISITOR = "DATE";
    public static final String COL_TIMEVISITOR = "TIME";
    public static final String COL_TEACHERVISITOR = "TEACHER";
    public static final String COL_BRANCHVISITOR = "BRANCH";
    public static final String COL_IDPROOFVISITOR = "IDPROOF";
    public static final String COL_IDPROOFNUBERVISITOR = "IDPROOFNUMBER";
    public static final String COL_VEHICLEVISITOR = "VEHICLE";
    public static final String COL_VEHICLENUMBERVISITOR = "VEHICLENUMBER";

    public static final String CREATE_TAB_QUERYVISITORENTRY = "create table visitorentry(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(20)," +
            "EMAIL varchar(256)," +
            "GENDER varchar(10)," +
            "ADDRESS varchar(256)," +
            "PURPOSE varchar(256)," +
            "DATE varchar(30)," +
            "TIME varchar(30)," +
            "TEACHER varchar(256)," +
            "BRANCH varchar(256)," +
            "IDPROOF varchar(256)," +
            "IDPROOFNUMBER varchar(256)," +
            "VEHICLE varchar(256)," +
            "VEHICLENUMBER varchar(256)" +
            ")";

    public static final String PREFS_NAME = "visitorbook";
    public static final String KEY_NAME = "keyName";
    public static final String KEY_PHONE = "keyPhone";
    public static final String KEY_EMAIL = "keyEmail";
    public static final String KEY_GENDER = "keyGender";
    public static final String KEY_ADDRESS = "keyAddress";
    public static final String KEY_PURPOSE = "keyPurpose";
    public static final String KEY_DATE = "keyDate";
    public static final String KEY_TIME = "keyTime";
    public static final String KEY_TEACHER = "keyTeacher";
    public static final String KEY_BRANCH = "keyBranch";
    public static final String KEY_IDPROOF = "keyIDProof";
    public static final String KEY_IDPROOFNUMBER = "keyIDProofNumber";
    public static final String KEY_VEHICLE = "keyVehicle";
    public static final String KEY_VEHICLENUMBER = "keyVehicleNumber";

    // URI
    public static final Uri VISITORENTRY_URI = Uri.parse("content://auribises.com.auribises.visitorentry.visitorentryprovider/"+TAB_NAMEVISITOR);

    final static String URI1 = "http://tajinderj.esy.es/visitorentry/";

    // URL
    public static final String INSERT_VISITORENTRY_PHP = "http://tajinderj.esy.es/visitorentry/insert.php";
    public static final String RETRIEVE_VISITORENTRY_PHP = "http://tajinderj.esy.es/visitorentry/retrieve.php";
    public static final String DELETE_VISITORENTRY_PHP = "http://tajinderj.esy.es/visitorentry/delete.php";
    public static final String UPDATE_VISITORENTRY_PHP = "http://tajinderj.esy.es/visitorentry/update.php";

    // 5. Information for adminlogin Database
    public static final int DB_VERSIONADMIN = 1;
    public static final String DB_NAMEADMIN = "adminlogin.db";

    // Information for Login Table
    public static final String TAB_NAMEADMINLOGIN = "Login";
    public static final String COL_IDADMINLOGIN = "_ID";
    public static final String COL_USERNAMEADMINLOGIN = "USERNAME";
    public static final String COL_PASSWORDADMINLOGIN = "PASSWORD";

    // URI
    public static final Uri ADMINLOGIN_URI = Uri.parse("content://com.auribises.Adminappointment.teacherprovider/"+TAB_NAMEADMINLOGIN);

    final static String URIADMINLOGIN = "http://tajinderj.esy.es/Adminlogin/";

    // URL
    public static final String ADMINLOGIN_PHP = "http://tajinderj.esy.es/Adminlogin/login.php";

    // 6. Information for guardlogin Database
    public static final int DB_VERSIONGUARD = 1;
    public static final String DB_NAMEGUARD = "guardlogin.db";

    // Information for Login Table
    public static final String TAB_NAMEGUARDLOGIN = "Login";
    public static final String COL_IDGUARDLOGIN = "_ID";
    public static final String COL_USERNAMEGUARDLOGIN = "USERNAME";
    public static final String COL_PASSWORDGUARDLOGIN = "PASSWORD";

    // URI
    public static final Uri GUARDLOGIN_URI = Uri.parse("content://com.auribises.Adminappointment.teacherprovider/"+TAB_NAMEGUARDLOGIN);

    final static String URIGUARD = "http://tajinderj.esy.es/Guardlogin/";

    // URL
    public static final String LOGINGUARD_PHP = "http://tajinderj.esy.es/Guardlogin/login.php";

    // 7. Information for RegisterAdmin Database
    public static final int DB_VERSIONREGISTERADMIN = 1;
    public static final String DB_NAMEREGISTERADMIN = "RegisterAdmin.db";

    // Information for RegisterGuard Table
    public static final String TAB_NAMEREGISTERADMIN = "RegisterAdmin";
    public static final String COL_IDREGISTERADMIN = "_ID";
    public static final String COL_NAMEREGISTERADMIN = "NAME";
    public static final String COL_PHONEREGISTERADMIN = "PHONE";
    public static final String COL_EMAILREGISTERADMIN = "EMAIL";
    public static final String COL_BIRTHDATEREGISTERADMIN = "BIRTH_DATE";
    public static final String COL_GENDERREGISTERADMIN = "GENDER";
    public static final String COL_ADDRESSREGISTERADMIN = "ADDRESS";
    public static final String COL_QUALIFICATIONREGISTERADMIN = "QUALIFICATION";
    public static final String COL_EXPERIENCEREGISTERADMIN = "EXPERIENCE";

    public static final String CREATE_TAB_QUERYREGISTERADMIN = "create table RegisterAdmin(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(256)," +
            "EMAIL varchar(256)," +
            "BIRTHDATE varchar(256)," +
            "GENDER varchar(256)," +
            "ADDRESS varchar(256)," +
            "QUALIFICATION varchar(256)," +
            "EXPERIENCE varchar(256)" +
            ")";

    // URI
    public static final Uri REGISTERADMIN_URI = Uri.parse("content://com.auribises.RegisterAdmin.teacherprovider/"+TAB_NAMEREGISTERADMIN);

    // URL
    public static final String INSERT_REGISTERADMIN_PHP = "http://tajinderj.esy.es/registeradmin/insert.php";

    // 8. Information for RegisterGuard Database
    public static final int DB_VERSIONREGISTERGUARD = 1;
    public static final String DB_NAMEREGISTERGUARD = "RegisterGuard.db";

    // Information for RegisterGuard Table
    public static final String TAB_NAMEREGISTERGUARD = "RegisterGuard";
    public static final String COL_IDREGISTERGUARD = "_ID";
    public static final String COL_NAMEREGISTERGUARD = "NAME";
    public static final String COL_PHONEREGISTERGUARD = "PHONE";
    public static final String COL_EMAILREGISTERGUARD = "EMAIL";
    public static final String COL_BIRTHDATEREGISTERGUARD = "BIRTH_DATE";
    public static final String COL_GENDERREGISTERGUARD = "GENDER";
    public static final String COL_ADDRESSREGISTERGUARD = "ADDRESS";
    public static final String COL_QUALIFICATIONREGISTERGUARD = "QUALIFICATION";
    public static final String COL_EXPERIENCEREGISTERGUARD = "EXPERIENCE";

    public static final String CREATE_TAB_QUERYREGISTERGUARD = "create table RegisterGuard(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(256)," +
            "EMAIL varchar(256)," +
            "BIRTHDATE varchar(256)," +
            "GENDER varchar(256)," +
            "ADDRESS varchar(256)," +
            "QUALIFICATION varchar(256)," +
            "EXPERIENCE varchar(256)" +
            ")";

    // URI
    public static final Uri REGISTERGUARD_URI = Uri.parse("content://com.auribises.RegisterGuard.teacherprovider/"+TAB_NAMEREGISTERGUARD);

    // URL
    public static final String INSERT_REGISTERGUARD_PHP = "http://tajinderj.esy.es/registerguard/insert.php";
    public static final String RETRIEVE_REGISTERGUARD_PHP = "http://tajinderj.esy.es/registerguard/retrieve.php";
    public static final String DELETE_REGISTERGUARD_PHP = "http://tajinderj.esy.es/registerguard/delete.php";
    public static final String UPDATE_REGISTERGUARD_PHP = "http://tajinderj.esy.es/registerguard/update.php";

    // 9. Information for RegisterTeacher Database
    public static final int DB_VERSIONREGISTERTEACHER = 1;
    public static final String DB_NAMEREGISTERTEACHER = "RegisterTeacher.db";

    // Information for RegisterTeacher Table
    public static final String TAB_NAMEREGISTERTEACHER = "RegisterTeacher";
    public static final String COL_IDREGISTERTEACHER = "_ID";
    public static final String COL_NAMEREGISTERTEACHER = "NAME";
    public static final String COL_PHONEREGISTERTEACHER = "PHONE";
    public static final String COL_EMAILREGISTERTEACHER = "EMAIL";
    public static final String COL_BIRTHDATEREGISTERTEACHER = "BIRTH_DATE";
    public static final String COL_GENDERREGISTERTEACHER = "GENDER";
    public static final String COL_ADDRESSREGISTERTEACHER = "ADDRESS";
    public static final String COL_QUALIFICATIONREGISTERTEACHER = "QUALIFICATION";
    public static final String COL_EXPERIENCEREGISTERTEACHER = "EXPERIENCE";

    public static final String CREATE_TAB_QUERYREGISTERTEACHER = "create table RegisterTeacher(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(256)," +
            "EMAIL varchar(256)," +
            "BIRTHDATE varchar(256)," +
            "GENDER varchar(256)," +
            "ADDRESS varchar(256)," +
            "QUALIFICATION varchar(256)," +
            "EXPERIENCE varchar(256)" +
            ")";

    //     REGISTER TEACHER
    public static final String KEY_NAMEREGISTERTEACHER = "keyName";
    public static final String KEY_PHONEREGISTERTEACHER = "keyPhone";
    public static final String KEY_EMAILREGISTERTEACHER = "keyEmail";
    public static final String KEY_BIRTHDATEREGISTERTEACHER = "keyBirthdate";
    public static final String KEY_GENDERREGISTERTEACHER = "keyGender";
    public static final String KEY_ADDRESSREGISTERTEACHER = "keyAddress";
    public static final String KEY_PURPOSEREGISTERTEACHER = "keyQualification";
    public static final String KEY_DATEREGISTERTEACHER = "keyExperience";

    // URI
    public static final Uri REGISTERTEACHER_URI = Uri.parse("content://com.auribises.RegisterTeacher.teacherprovider/"+TAB_NAMETEACHER);

    // URL
    public static final String INSERT_REGISTERTEACHER_PHP = "http://tajinderj.esy.es/registerteacher/insert.php";
    public static final String RETRIEVE_REGISTERTEACHER_PHP = "http://tajinderj.esy.es/registerteacher/retrieve.php";
    public static final String DELETE_REGISTERTEACHER_PHP = "http://tajinderj.esy.es/registerteacher/delete.php";
    public static final String UPDATE_REGISTERTEACHER_PHP = "http://tajinderj.esy.es/registerteacher/update.php";

    // 10. Information for adminappointment Table
    public static final String TAB_NAMEADMIN = "adminappointment";
    public static final String COL_IDADMIN = "_ID";
    public static final String COL_NAMEADMIN = "NAME";
    public static final String COL_PHONEADMIN = "PHONE";
    public static final String COL_EMAILADMIN = "EMAIL";
    public static final String COL_GENDERADMIN = "GENDER";
    public static final String COL_PURPOSEADMIN = "PURPOSE";
    public static final String COL_DATEADMIN = "DATE";
    public static final String COL_TIMEADMIN = "TIME";
    public static final String COL_ROOMADMIN = "ROOM";

    public static final String CREATE_TAB_QUERYADMIN = "create table adminappointment(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(256)," +
            "EMAIL varchar(256)," +
            "GENDER varchar(256)," +
            "PURPOSE varchar(256)," +
            "DATE varchar(256)," +
            "TIME varchar(256)," +
            "ROOM varchar(256)" +
            ")";

    // URI
    public static final Uri ADMIN_APPOINTMENT_URI = Uri.parse("content://auribises.com.adminappointment.teacherprovider/"+TAB_NAMEADMIN);

    // URL
    public static final String INSERT_ADMIN_APPOINTMENT_PHP = "http://tajinderj.esy.es/adminappointment/insert.php";
    public static final String RETRIEVE_ADMIN_APPOINTMENT_PHP = "http://tajinderj.esy.es/adminappointment/retrieve.php";
    public static final String DELETE_ADMIN_APPOINTMENT_PHP = "http://tajinderj.esy.es/adminappointment/delete.php";
    public static final String UPDATE_ADMIN_APPOINTMENT_PHP = "http://tajinderj.esy.es/adminappointment/update.php";

    public static final int REQCODE= 101;
    public static final int UPREQCODE= 103;
    public static final int UPRESCODE= 201;

    public static final String keyUpdate= "update";
    public static final String keyresult = "result";
}










