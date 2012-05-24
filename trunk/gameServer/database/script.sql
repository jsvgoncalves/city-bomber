-- DROPS
DROP TABLE IF EXISTS bomberuser;
DROP TABLE IF EXISTS server;
DROP TABLE IF EXISTS session;

--
-- Servers table
--

CREATE TABLE server (
    ip TEXT NOT NULL,
    port INTEGER NOT NULL,
    name VARCHAR(32) NOT NULL,
    lastUpdated timestamp NOT NULL DEFAULT '2000-01-01 00:00:00'
);

INSERT INTO server
        (ip, port, name)
    VALUES
        ('192.168.1.89', '8080', 'Hades');


--
-- Sessions table
--
CREATE TABLE session (
    sid SERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL DEFAULT 'New Session',
    maxplayers INTEGER NOT NULL DEFAULT 10,
    joined INTEGER NOT NULL DEFAULT 0,
    private BOOLEAN NOT NULL DEFAULT false
);

INSERT INTO session
        (name, maxplayers, joined, private)
    VALUES
        ('Novo Jogo', 12, 0, false),
        ('Jogo de bombas', 5, 0, false);


--
-- Users table
--
CREATE TABLE bomberuser (
    id SERIAL PRIMARY KEY,
    username VARCHAR(32) UNIQUE NOT NULL,
    sessionid INTEGER NOT NULL,
    FOREIGN KEY (sessionid) REFERENCES session(sid)
);