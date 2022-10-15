package com.kotakcherry.stocksearch.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AppErrorCodes {

  INVALID_FILE_FORMAT("File format is invalid. Please upload an excel file!", HttpStatus.BAD_REQUEST),
  INVALID_MARKET_CAP("Market cap is not valid. Please enter a valid Market cap", HttpStatus.BAD_REQUEST),
  INVALID_CLOSE_PRICE("Closing price is not valid. Please enter a valid close price", HttpStatus.BAD_REQUEST),
  INVALID_PE_RATIO("PE ratio is not valid. Please enter a valid PE ratio", HttpStatus.BAD_REQUEST),
  INVALID_STOCK_ID("Please enter a valid stock id", HttpStatus.BAD_REQUEST),
  UPLOAD_FAILED("Could not upload the file. Please try after sometime", HttpStatus.EXPECTATION_FAILED);

  private String message;
  private HttpStatus httpStatus;
}
