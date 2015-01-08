import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author zhaowen.zhuang
 *
 */
public class Main {
    public static void main(String[] args) {
        switchUser("246", "158");
        switchUser("248", "160");
        switchUser("244", "162");
        switchUser("222", "164");
        switchUser("268", "166");
        switchUser("300", "168");
        switchUser("306", "170");
        switchUser("386", "58");
        switchUser("438", "172");
        switchUser("444", "174");
        switchUser("462", "176");
        switchUser("312", "178");
        switchUser("520", "180");
        switchUser("530", "182");
        switchUser("532", "184");
        switchUser("484", "186");
        switchUser("534", "188");
        switchUser("566", "190");
        switchUser("642", "192");
        switchUser("634", "194");
        switchUser("664", "196");
        switchUser("670", "198");
        switchUser("686", "300");
        switchUser("708", "202");
        switchUser("712", "204");
        switchUser("720", "206");
        switchUser("778", "208");
        switchUser("832", "201");
    }

    private static void switchUser(String oid, String nid) {
        List<String> patterns = new ArrayList<String>();
        patterns.add("update tdp_developer set account_id = '%s' where account_id = '%s';");
        patterns.add("update tdp_developer_released set account_id = '%s' where account_id = '%s';");
        patterns.add("update tdp_app set dev_account_id = '%s' where dev_account_id = '%s';");
        patterns.add("update tdp_app_released set dev_account_id = '%s' where dev_account_id = '%s';");
        patterns.add("update tdp_apk set account_id = '%s' where account_id = '%s';");
        patterns.add("update tdp_apk_released set account_id = '%s' where account_id = '%s';");
        patterns.add("update tdp_apk_tmp set account_id = '%s' where account_id = '%s';");
        patterns.add("update tdp_public_key set account_id = '%s' where account_id = '%s';");
        patterns.add("update tdp_sys_message set account_id = '%s' where account_id = '%s';");
        for (String pattern : patterns) {
            System.out.println(String.format(pattern, nid, oid));
        }
        System.out.println("-- -- -- ");
    }
}
