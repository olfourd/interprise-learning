db.createUser(
    {
        user: "olfd",
        pwd: "olfd",
        roles: [
            {
                role: "dbAdmin",
                db: "admin"
            }
        ]
    }
);