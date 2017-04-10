package com.sxk.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.sxk.common.utils.MailUtils;
import com.sxk.common.utils.MailUtils.Mail;

public class MailTest {

    public static void main(String[] args) {

        long starttime = System.currentTimeMillis();
        System.out.println("开始发邮件...");
        Mail mail = new Mail();
        //        mail.serverName = "";
        //        mail.server = "smtp.exmail.qq.com";
        //        mail.password = "lvtu1233211";
        //        mail.user = "apps@pixshow.net";

        mail.serverName = "";
        mail.server = "smtp.qq.com";
        mail.password = "pztrzomjnshybehi";
        mail.user = "961830745@qq.com";

        mail.title = "呵呵";
        //mail.to = "sxk5245@126.com,1871025980@qq.com";
        mail.to = "sxk5245@126.com";

        Map<String, File> attachment = new HashMap<>();
        File file = new File("D://001.jpg");
        attachment.put("001", file);

        mail.attachment = attachment;

        StringBuilder content = new StringBuilder();
        content.append("<table>");

        content.append("<tr><th colspan='4' bgcolor='gray'>用户</th></tr>");
        content.append("<tr><th>用户昵称</th><td>" + "孙" + "</td></tr>");
        content.append("<tr><th>联系电话</th><td>" + "18778859625" + "</td></tr>");
        content.append("<tr><th>联系邮箱</th><td>" + "113@qq.com" + "</td></tr>");

        content.append("</table>");

        mail.content = content.toString();
        MailUtils.send(mail);

        System.out.println("邮件发送成功,用时：" + (System.currentTimeMillis() - starttime));
    }
}
