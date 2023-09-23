package com.itself.responsibility;

/**
 * 抽象处理者
 */
abstract class Handler {
    protected Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(Request request);
}

/**
 * 具体处理者A
 */
class ConcreteHandlerA extends Handler {
    @Override
    public void handleRequest(Request request) {
        if (request.getType().equals("A")) {
            System.out.println("ConcreteHandlerA handles the content: " + request.getContent());
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}

/**
 *  具体处理者B
 */
class ConcreteHandlerB extends Handler {
    @Override
    public void handleRequest(Request request) {
        if (request.getType().equals("B")) {
            System.out.println("ConcreteHandlerB handles the content: " + request.getContent());
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}

/**
 * 请求类
 */
class Request {
    private String type;
    private String content;

    public Request(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}

/**
 * 责任链模式demo
 */
public class Demo {
    public static void main(String[] args) {
        Handler handlerA = new ConcreteHandlerA();
        Handler handlerB = new ConcreteHandlerB();

        handlerA.setNextHandler(handlerB);

        // 创建请求
        Request request1 = new Request("A", "Request A");
        Request request2 = new Request("B", "Request B");
        Request request3 = new Request("C", "Request C");

        // 发送请求
        handlerA.handleRequest(request1);  // ConcreteHandlerA handles the content: Request A
        handlerA.handleRequest(request2);  // ConcreteHandlerB handles the content: Request B
        handlerA.handleRequest(request3);  // No handler can handle the request.
    }
}