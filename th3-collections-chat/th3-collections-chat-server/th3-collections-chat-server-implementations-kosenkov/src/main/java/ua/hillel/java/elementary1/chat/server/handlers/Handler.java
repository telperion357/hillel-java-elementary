package ua.hillel.java.elementary1.chat.server.handlers;

import ua.hillel.java.elementary1.chat.common.model.ErrorResponse;
import ua.hillel.java.elementary1.chat.common.model.Request;

public interface Handler<REQ extends Request, RES extends ErrorResponse> {

    RES handleEvent(REQ request);
}
