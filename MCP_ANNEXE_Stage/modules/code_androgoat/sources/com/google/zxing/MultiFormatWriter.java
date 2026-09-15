package com.google.zxing;

import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.Code93Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.oned.UPCEWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public final class MultiFormatWriter implements Writer {
    @Override // com.google.zxing.Writer
    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) throws WriterException {
        return encode(contents, format, width, height, null);
    }

    @Override // com.google.zxing.Writer
    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        Writer writer;
        switch (format) {
            case EAN_8:
                Writer writer2 = new EAN8Writer();
                writer = writer2;
                break;
            case UPC_E:
                Writer writer3 = new UPCEWriter();
                writer = writer3;
                break;
            case EAN_13:
                Writer writer4 = new EAN13Writer();
                writer = writer4;
                break;
            case UPC_A:
                Writer writer5 = new UPCAWriter();
                writer = writer5;
                break;
            case QR_CODE:
                Writer writer6 = new QRCodeWriter();
                writer = writer6;
                break;
            case CODE_39:
                Writer writer7 = new Code39Writer();
                writer = writer7;
                break;
            case CODE_93:
                Writer writer8 = new Code93Writer();
                writer = writer8;
                break;
            case CODE_128:
                Writer writer9 = new Code128Writer();
                writer = writer9;
                break;
            case ITF:
                Writer writer10 = new ITFWriter();
                writer = writer10;
                break;
            case PDF_417:
                Writer writer11 = new PDF417Writer();
                writer = writer11;
                break;
            case CODABAR:
                Writer writer12 = new CodaBarWriter();
                writer = writer12;
                break;
            case DATA_MATRIX:
                Writer writer13 = new DataMatrixWriter();
                writer = writer13;
                break;
            case AZTEC:
                Writer writer14 = new AztecWriter();
                writer = writer14;
                break;
            default:
                throw new IllegalArgumentException("No encoder available for format ".concat(String.valueOf(format)));
        }
        return writer.encode(contents, format, width, height, hints);
    }
}
