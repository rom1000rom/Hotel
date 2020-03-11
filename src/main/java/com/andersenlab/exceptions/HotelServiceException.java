package com.andersenlab.exceptions;


/**Класс исключения, возникающего при работе сервисов
 @author Артемьев Р.А.
 @version 11.03.2020 */
public class HotelServiceException extends RuntimeException {

    public HotelServiceException()
    {
    }

    public HotelServiceException(String message)
    {
        super(message);
    }

    public HotelServiceException(Throwable cause)
    {
        super(cause);
    }

    public HotelServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
