package cn.mojit.fragmentcode.infrastructure.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.svg.SVGCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.w3c.dom.DocumentFragment;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BarQrCodeService {

    /**
     * 条形码
     *
     * @param code
     * @return
     * @throws TransformerException
     * @throws IOException
     */
    public static String getBarCodeSVGStr(String code) throws Exception {
        Double moduleWidth = Double.valueOf(1.0D);
        //分辨率
        double dpi = 300;
        Double width = 6d;
        Code128Bean bean = new Code128Bean();
        String barcode = code;
        Integer barcodeLength = Integer.valueOf(barcode.length());
        if (barcodeLength.intValue() < 8) {
            moduleWidth = Double.valueOf(2.0D * width.doubleValue());
        } else if (barcodeLength.intValue() < 16) {
            moduleWidth = width;
        }
        if (moduleWidth.doubleValue() < 1.0D) {
            moduleWidth = Double.valueOf(1.0D);
        }
        bean.setModuleWidth(UnitConv.in2mm(moduleWidth.doubleValue() / dpi));
        bean.setBarHeight(30);// 高度
        bean.setQuietZone(60); // 向右偏移
        bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        // 设置两侧是否加空白
        bean.doQuietZone(false);
        SVGCanvasProvider canvas = new SVGCanvasProvider(true, 0);
        bean.generateBarcode(canvas, barcode);
        DocumentFragment frag = canvas.getDOMFragment();
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer trans = factory.newTransformer();
        Source src = new DOMSource(frag);
        StringWriter outw = new StringWriter();
        trans.transform(src, new StreamResult(outw));
        return outw.toString();

    }

    /**
     * 条形码写流
     *
     * @param response
     * @param code
     * @throws TransformerException
     * @throws IOException
     */
    public static void getBarCodeSVGStrOut(HttpServletResponse response, String code) throws Exception {
        Double moduleWidth = Double.valueOf(1.0D);
        //分辨率
        double dpi = 300;
        Double width = 6d;
        Code128Bean bean = new Code128Bean();
        String barcode = code;
        Integer barcodeLength = Integer.valueOf(barcode.length());
        if (barcodeLength.intValue() < 8) {
            moduleWidth = Double.valueOf(2.0D * width.doubleValue());
        } else if (barcodeLength.intValue() < 16) {
            moduleWidth = width;
        }
        if (moduleWidth.doubleValue() < 1.0D) {
            moduleWidth = Double.valueOf(1.0D);
        }
        bean.setModuleWidth(UnitConv.in2mm(moduleWidth.doubleValue() / dpi));
        bean.setBarHeight(30);// 高度
        bean.setQuietZone(60); // 向右偏移
        bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        // 设置两侧是否加空白
        bean.doQuietZone(false);
        SVGCanvasProvider canvas = new SVGCanvasProvider(true, 0);
        bean.generateBarcode(canvas, barcode);
        DocumentFragment frag = canvas.getDOMFragment();
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer trans = factory.newTransformer();
        Source src = new DOMSource(frag);
        trans.transform(src, new StreamResult(response.getOutputStream()));
        //请勿删除
    }

    /**
     * 二维码
     *
     * @param code
     * @param size
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static String getqrSVGStr(String code, int size) throws Exception {
        if (code!=null){
            return createQRStr(code, size);
        }
        return null;
    }

    /**
     * 二维码 写流
     *
     * @param response
     * @param code
     * @param size
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static void getqrSVGStrOut(HttpServletResponse response, String code, int size) throws Exception {
        response.getOutputStream().write(createQRStr(code, size).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成svg字符串
     *
     * @param code
     * @param size
     * @return
     * @throws Exception
     */
    public static String createQRStr(String code, Integer size) throws Exception {
        if (size==null){
            size=60;
        }
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        BitMatrix bitMatrix = qrCodeWriter.encode(code, BarcodeFormat.QR_CODE, size, size, hints);
        StringBuilder sbPath = new StringBuilder();
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BitArray row = new BitArray(width);
        for (int y = 0; y < height; ++y) {
            row = bitMatrix.getRow(y, row);
            for (int x = 0; x < width; ++x) {
                if (row.get(x)) {
                    sbPath.append(" M" + x + "," + y + "h1v1h-1z");
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" viewBox=\"0 0 ").append(width).append(" ").append(height).append("\" stroke=\"none\">\n");
        sb.append("<style type=\"text/css\">\n");
        sb.append(".black {fill:#000000;}\n");
        sb.append("</style>\n");
        sb.append("<path class=\"black\"  d=\"").append(sbPath).append("\"/>\n");
        sb.append("</svg>\n");
        return sb.toString();
    }
    public static String createQRStr1(String code, Integer size) throws Exception {
        if (size==null){
            size=60;
        }
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        BitMatrix bitMatrix = qrCodeWriter.encode(code, BarcodeFormat.QR_CODE, size, size, hints);
        StringBuilder sbPath = new StringBuilder();
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BitArray row = new BitArray(width);
        for (int y = 0; y < height; ++y) {
            row = bitMatrix.getRow(y, row);
            for (int x = 0; x < width; ++x) {
                if (row.get(x)) {
                    sbPath.append(" M" + x + "," + y + "h1v1h-1z");
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" viewBox=\"0 0 ").append(width).append(" ").append(height).append("\" stroke=\"none\">\n");
        sb.append("<path class=\"black\"  d=\"").append(sbPath).append("\"/>\n");
        sb.append("</svg>\n");
        return sb.toString();
    }

}
