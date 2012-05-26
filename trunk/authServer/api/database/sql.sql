--
-- Servers table
--
DROP TABLE IF EXISTS Server;

CREATE TABLE Server (
    id SERIAL PRIMARY KEY,
    ip VARCHAR(15) NOT NULL,
    port INTEGER NOT NULL,
    name VARCHAR(32) NOT NULL,
    lastUpdated timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
    UNIQUE(ip, port)
);

INSERT INTO Server
        (ip, port, name)
    VALUES
        ('172.30.70.62', '8080', 'Hades'),
        ('172.30.70.63', '8080', 'Poseidon'),
        ('172.30.70.64', '8080', 'Helios'),
        ('172.30.70.66', '8080', 'Zeus'),
        ('172.30.70.67', '8080', 'Apollo'),
        ('172.30.70.68', '8080', 'Perseus')
    ;
--
-- Users table
--
DROP TABLE IF EXISTS BomberUser;

CREATE TABLE BomberUser (
    id SERIAL PRIMARY KEY,
    username VARCHAR(32) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL,
    salt VARCHAR(32) NOT NULL, -- 32 bytes de salt para a encriptacao
    email VARCHAR(255) NOT NULL,
    fbID VARCHAR(64) UNIQUE,
    faceUser BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO BomberUser
        (username, email, faceUser, fbID, password, salt)
    VALUES
        ('joao', 'jsvgoncalves@gmail.com', TRUE, '100001343377064', 'b75b80c624290d50656849dfbf8ead31cefacd34ec0b62138774994cb103d09cb2842d427babc5b50625760eb3b25e510381b08907ee96336aed95b92a5af02e' , 'c5805bfdb413b824f225e15acae108b1'),
        ('lopes', 'joaopclopes@gmail.com', TRUE, '1371707488', 'b75b80c624290d50656849dfbf8ead31cefacd34ec0b62138774994cb103d09cb2842d427babc5b50625760eb3b25e510381b08907ee96336aed95b92a5af02e' , 'c5805bfdb413b824f225e15acae108b1');
INSERT INTO BomberUser
        (username, email, password, salt)
    VALUES        
        ('artur', 'arturngomes@gmail.com', 'b75b80c624290d50656849dfbf8ead31cefacd34ec0b62138774994cb103d09cb2842d427babc5b50625760eb3b25e510381b08907ee96336aed95b92a5af02e' , 'c5805bfdb413b824f225e15acae108b1'),
        ('vitor', 'vitor@vitor.com', 'b75b80c624290d50656849dfbf8ead31cefacd34ec0b62138774994cb103d09cb2842d427babc5b50625760eb3b25e510381b08907ee96336aed95b92a5af02e' , 'c5805bfdb413b824f225e15acae108b1')        
    ;