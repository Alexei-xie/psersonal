package com.itself.strategy;

/**
 * 策略代理
 * @Author xxw
 * @Date 2022/06/08
 */
public class Strategy {
    private Person person;

    public void setPerson(Person person) {
        this.person = person;
    }

    public void execute() {
        if (person != null) {
            person.action();
        }
    }
}
