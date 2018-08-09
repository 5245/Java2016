package td.enterprise.codemagic.factory;

import td.enterprise.codemagic.factory.def.FtlDef;

public class CodeUtil {

    public CodeUtil() {
    }

    public static void main(String args[]) {
        String codeGeneratePath = "D:/git/codetest/";

//        String tableName = "rfm_brand_dict";
//        String codeName = "rfm_brand_dict";
//        String entityPackage = "rfm";
//        DbCodeGenerateFactory.codeGenerate(codeGeneratePath, tableName, codeName, entityPackage, FtlDef.KEY_TYPE_02);
//
//        tableName = "rfm_channel_dict";
//        codeName = "rfm_channel_dict";
//        entityPackage = "rfm";
//        DbCodeGenerateFactory.codeGenerate(codeGeneratePath, tableName, codeName, entityPackage, FtlDef.KEY_TYPE_02);
//
//        tableName = "rfm_reg_channel_dict";
//        codeName = "rfm_reg_channel_dict";
//        entityPackage = "rfm";
//        DbCodeGenerateFactory.codeGenerate(codeGeneratePath, tableName, codeName, entityPackage, FtlDef.KEY_TYPE_02);

        String tableName = "rfm_label";
        String codeName = "rfm_label";
        String entityPackage = "rfm";
        DbCodeGenerateFactory.codeGenerate(codeGeneratePath, tableName, codeName, entityPackage, FtlDef.KEY_TYPE_02);

        tableName = "rfm_life_cycle";
        codeName = "rfm_life_cycle";
        entityPackage = "rfm";
        DbCodeGenerateFactory.codeGenerate(codeGeneratePath, tableName, codeName, entityPackage, FtlDef.KEY_TYPE_02);

        tableName = "rfm_monetary";
        codeName = "rfm_monetary";
        entityPackage = "rfm";
        DbCodeGenerateFactory.codeGenerate(codeGeneratePath, tableName, codeName, entityPackage, FtlDef.KEY_TYPE_02);


        tableName = "rfm_score";
        codeName = "rfm_score";
        entityPackage = "rfm";
        DbCodeGenerateFactory.codeGenerate(codeGeneratePath, tableName, codeName, entityPackage, FtlDef.KEY_TYPE_02);

    }
}
