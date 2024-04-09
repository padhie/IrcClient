package de.padhie.ircclient.Client;

public class NoopClient implements ClientInterface {
    private final boolean login;

    public NoopClient(boolean login) {
        this.login = login;
    }

    @Override
    public boolean login () {
        return this.login;
    }

    @Override
    public void join () {
    }

    @Override
    public void start () {
    }

    @Override
    public void leave () {
    }
}
