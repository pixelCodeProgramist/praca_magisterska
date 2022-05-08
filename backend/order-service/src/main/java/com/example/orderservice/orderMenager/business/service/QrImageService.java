package com.example.orderservice.orderMenager.business.service;

import com.example.orderservice.orderMenager.business.exception.order.CannotCreateQRException;
import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.orderMenager.data.repository.UserOrderRepo;
import com.example.orderservice.security.business.request.UserByIdRequest;
import com.example.orderservice.security.business.service.JwtTokenNonUserOrderProvider;
import com.example.orderservice.security.business.service.JwtTokenNonUserProvider;
import com.example.orderservice.userMenager.api.request.User;
import com.example.orderservice.userMenager.feignClient.UserServiceFeignClient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@AllArgsConstructor
public class QrImageService {
    private JwtTokenNonUserOrderProvider jwtTokenNonUserOrderProvider;
    private JwtTokenNonUserProvider jwtTokenNonUserProvider;
    private UserServiceFeignClient userServiceFeignClient;

    private UserOrderRepo userOrderRepo;
    private final int QR_WIDTH = 400;
    private final int QR_HEIGHT = 400;

    public byte[] createQrImage(UserOrder userOrder, User user) {
        try {
            if(user!=null)
                return getQRCodeImage(userOrder.getTransactionToken());
            throw new CannotCreateQRException();
        } catch (IOException | WriterException ex) {
            throw new CannotCreateQRException();
        }
    }

    public byte[] getQRCodeImage(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig() ;

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream,con);
        return pngOutputStream.toByteArray();
    }
}
