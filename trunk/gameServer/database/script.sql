-- DROPS
DROP TABLE bomberuser;
DROP TABLE server;
DROP TABLE session;

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
        ('172.30.70.62', '8080', 'Hades');


--
-- Sessions table
--
CREATE TABLE session (
    sid SERIAL PRIMARY KEY,
    nome VARCHAR(32) DEFAULT 'New Session',
    maxplayers INTEGER DEFAULT 10,
    userid INTEGER NOT NULL,
    FOREIGN KEY (userid) REFERENCES bomberuser(id)
);

INSERT INTO session
        (nome, maxplayers)
    VALUES
        ('Novo Jogo', 12),
        ('Jogo de bombas', 5);


--
-- Users table
--
CREATE TABLE bomberuser (
    id SERIAL PRIMARY KEY,
    username VARCHAR(32) UNIQUE NOT NULL,
    sessionid INTEGER NOT NULL,
    FOREIGN KEY (sessionid) REFERENCES session(sid)
);