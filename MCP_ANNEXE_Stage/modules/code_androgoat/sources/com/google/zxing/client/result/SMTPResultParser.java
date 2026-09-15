package com.google.zxing.client.result;

import com.google.zxing.Result;

/* JADX INFO: loaded from: classes2.dex */
public final class SMTPResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public EmailAddressParsedResult parse(Result result) {
        String body;
        String subject;
        String rawText = getMassagedText(result);
        if (rawText.startsWith("smtp:") || rawText.startsWith("SMTP:")) {
            String emailAddress = rawText.substring(5);
            int colon = emailAddress.indexOf(58);
            if (colon < 0) {
                body = null;
                subject = null;
            } else {
                String subject2 = emailAddress.substring(colon + 1);
                emailAddress = emailAddress.substring(0, colon);
                int colon2 = subject2.indexOf(58);
                if (colon2 < 0) {
                    body = null;
                    subject = subject2;
                } else {
                    String body2 = subject2.substring(colon2 + 1);
                    body = body2;
                    subject = subject2.substring(0, colon2);
                }
            }
            return new EmailAddressParsedResult(new String[]{emailAddress}, null, null, subject, body);
        }
        return null;
    }
}
