SET foreign_key_checks = 0;

create table app_settings
(
    id          int auto_increment
        primary key,
    name        varchar(45)                           not null,
    code        varchar(45)                           not null,
    value       varchar(45)                           not null,
    flag        varchar(45)                           not null,
    description varchar(255)                          not null,
    created_on  timestamp default current_timestamp() not null,
    updated_on  timestamp                             null,
    updated_by  int                                   null
);

create table audit_log
(
    id          bigint auto_increment
        primary key,
    description varchar(255)                            not null,
    created_by  int                                     null,
    flag        varchar(50) default '1'                 null,
    created_on  timestamp   default current_timestamp() not null,
    updated_on  timestamp   default current_timestamp() not null,
    shop_id     bigint                                  null,
    origin      varchar(45)                             null,
    ip          varchar(100)                            null
)
    charset = utf8mb3;

create table business_sell_configs
(
    id           bigint auto_increment
        primary key,
    config_name  varchar(255) null,
    config_value tinyint      null,
    created_by   int          null,
    created_on   datetime     null,
    shop_id      bigint       null,
    updated_by   int          null,
    updated_on   datetime     null
);

create table business_sms
(
    id             bigint auto_increment
        primary key,
    api_key        varchar(255) null,
    created_by     int          null,
    created_on     datetime     null,
    flag           varchar(255) null,
    message        varchar(255) null,
    message_ref    varchar(255) null,
    message_status varchar(255) null,
    phone_number   varchar(255) null,
    reason         varchar(255) null,
    sender_id      varchar(255) null,
    shop_id        bigint       null,
    updated_by     int          null,
    updated_on     datetime     null
);

create table business_sms_configs
(
    id             bigint auto_increment
        primary key,
    account_number varchar(255) null,
    api_key        varchar(255) null,
    created_by     int          null,
    created_on     datetime     null,
    flag           varchar(255) null,
    reason         varchar(255) null,
    sender_id      varchar(255) null,
    shop_id        bigint       null,
    updated_by     int          null,
    updated_on     datetime     null
);

create table cleared_debts
(
    id           bigint auto_increment
        primary key,
    check_out_id bigint         null,
    created_by   int            null,
    created_on   datetime       null,
    amount       decimal(19, 2) null,
    shop_id      bigint         null
);

create index FK5whw0fg4q7tfadxgq6awo63rr
    on cleared_debts (check_out_id);

create index FKadccxw6ge8o85f2mxaw8tieem
    on cleared_debts (shop_id);

create index FKkw96i6c71t8dlimbpjqnqj4jg
    on cleared_debts (created_by);

create table credit_payments
(
    id          int auto_increment
        primary key,
    customer_id int                                     not null,
    credit_id   int                                     not null,
    amount      decimal(18, 2)                          not null,
    balance     decimal(18, 2)                          not null,
    created_by  int                                     not null,
    shop_id     int                                     not null,
    flag        varchar(45) default '1'                 not null,
    created_on  timestamp   default current_timestamp() not null on update current_timestamp()
);

create table credits
(
    id                  int auto_increment
        primary key,
    customer_id         int                                     not null,
    sale_transaction_id int                                     not null,
    amount              decimal(18, 2)                          not null,
    balance             decimal(18, 2)                          not null,
    flag                varchar(45) default '1'                 not null,
    payment_status      varchar(45) default 'pending'           not null,
    shop_id             int                                     not null,
    created_by          int                                     not null,
    created_on          timestamp   default current_timestamp() not null
);

create table currencies
(
    id         int auto_increment
        primary key,
    currency   varchar(100)                            not null,
    code       varchar(100)                            not null,
    flag       varchar(45) default '1'                 null,
    created_by int                                     null,
    updated_by int                                     null,
    created_on timestamp   default current_timestamp() null,
    updated_on timestamp                               null
);

create table customer
(
    id          int auto_increment
        primary key,
    name        varchar(255)                          not null,
    phone       varchar(20)                           not null,
    created_on  timestamp default current_timestamp() not null on update current_timestamp(),
    updated_on  timestamp default current_timestamp() not null on update current_timestamp(),
    description varchar(255)                          null,
    shop_id     bigint                                not null,
    created_by  int                                   not null
);

create table debits
(
    id          bigint auto_increment
        primary key,
    amount      decimal(19, 2) null,
    created_by  int            null,
    created_on  datetime       null,
    debit       bit            null,
    description varchar(255)   null,
    expense     bit            null,
    shop_id     bigint         null
);

create index FK960sua8656uby4rhpvwk476q9
    on debits (shop_id);

create index FKpdi98m38aaif54rld25isn49l
    on debits (created_by);

create table expenses
(
    id          int auto_increment
        primary key,
    name        varchar(255)                          not null,
    description varchar(900)                          null,
    amount      decimal(18, 2)                        not null,
    created_by  int                                   not null,
    shop_id     int                                   not null,
    flag        varchar(45)                           not null,
    created_on  timestamp default current_timestamp() not null
);

create table hibernate_sequence
(
    next_val bigint null
);

create table invoices
(
    id           int auto_increment
        primary key,
    invoice_no   varchar(255)                            not null,
    invoice_date date                                    not null,
    status       varchar(45) default 'PAID'              not null,
    total        decimal(18, 2)                          not null,
    shop_id      int                                     not null,
    supplier_id  int                                     not null,
    created_by   int                                     not null,
    created_on   timestamp   default current_timestamp() not null
);

create table marketers_withdrawals_requests
(
    id             int auto_increment
        primary key,
    amount         decimal(18, 2) default 0.00                not null,
    flag           varchar(45)    default '1'                 not null,
    marketer_id    int                                        null,
    status         varchar(45)    default 'PENDING'           not null,
    created_by     int                                        not null,
    actioned_by    int                                        null,
    transaction_id int                                        null,
    action_reason  varchar(255)                               null,
    reference_no   varchar(255)                               null,
    updated_by     int                                        null,
    created_on     timestamp      default current_timestamp() null,
    updated_on     timestamp                                  null
);

create table mpesa_transactions
(
    id             int auto_increment
        primary key,
    reference_no   varchar(45)                                not null,
    transaction_id int                                        null,
    mpesa_ref      varchar(45)                                null,
    amount         decimal(18, 2) default 0.00                null,
    phone_number   varchar(45)                                null,
    narration      text                                       null,
    checkout_id    varchar(255)                               null,
    names          varchar(255)                               null,
    flag           varchar(45)    default '1'                 null,
    status         varchar(45)                                not null,
    created_by     int                                        null,
    updated_by     int                                        null,
    created_on     timestamp      default current_timestamp() null,
    updated_on     timestamp                                  null
);

create table notifications
(
    id              int auto_increment
        primary key,
    type            varchar(45)                             not null,
    shop_id         int                                     null,
    message         text                                    not null,
    phone_number    varchar(45)                             not null,
    message_id      varchar(255)                            not null,
    sent_status     varchar(45)                             not null,
    delivery_status varchar(45) default 'pending'           null,
    flag            varchar(45) default '1'                 not null,
    created_by      int                                     null,
    created_on      timestamp   default current_timestamp() not null
);

create table payment_methods
(
    id          int auto_increment
        primary key,
    name        varchar(50)                           not null,
    code        varchar(45)                           not null,
    description varchar(100)                          not null,
    is_active   tinyint(1)                            not null,
    created_on  timestamp default current_timestamp() not null on update current_timestamp()
);

create table persistent_logins
(
    username  varchar(64)                           not null,
    series    varchar(64)                           not null
        primary key,
    token     varchar(64)                           not null,
    last_used timestamp default current_timestamp() not null on update current_timestamp()
)
    charset = utf8mb3;

create table phone_verification
(
    id           int auto_increment
        primary key,
    phone_number varchar(45)                           not null,
    verified     tinyint                               not null,
    code         varchar(45)                           not null,
    created_on   timestamp default current_timestamp() not null
);

create table product_category
(
    id          int auto_increment
        primary key,
    name        varchar(50)  not null,
    description varchar(100) null,
    shop_id     bigint       null
);

create table product_price_trail
(
    id                    int auto_increment
        primary key,
    product_id            int            not null,
    shop_id               int            not null,
    created_by            int            not null,
    buying_price          decimal(18, 2) not null,
    selling_price         decimal(18, 2) not null,
    min_selling_price     decimal(18, 2) not null,
    new_buying_price      decimal(18, 2) not null,
    new_selling_price     decimal(18, 2) not null,
    new_min_selling_price decimal(18, 2) not null,
    type                  varchar(100)   not null,
    description           varchar(255)   null,
    created_on            timestamp      null
);

create table products
(
    id                int auto_increment
        primary key,
    name              varchar(50)                                not null,
    code              varchar(50)                                not null,
    description       varchar(100)                               not null,
    selling_price     decimal(18, 2) default 0.00                not null,
    buying_price      decimal(18, 2) default 0.00                not null,
    stock             int            default 0                   not null,
    category          int                                        not null,
    created_on        timestamp      default current_timestamp() not null on update current_timestamp(),
    updated_on        timestamp      default current_timestamp() not null,
    min_selling_price decimal(19, 2)                             null,
    re_order_level    int                                        null,
    expiry_date       datetime                                   null,
    flag              varchar(50)    default '1'                 null,
    shop_id           bigint                                     null,
    constraint products_ibfk_1
        foreign key (category) references product_category (id)
);

create table invoice_data
(
    id                int auto_increment
        primary key,
    product_id        int                                   not null,
    shop_id           int                                   not null,
    user_id           int                                   not null,
    selling_price     decimal(18, 2)                        null,
    min_selling_price decimal(18, 2)                        null,
    quantity          int                                   not null,
    buying_price      decimal(18, 2)                        not null,
    total             decimal(18, 2)                        not null,
    created_on        timestamp default current_timestamp() not null,
    constraint FKf501s87flsaalcole3gcjde67
        foreign key (product_id) references products (id)
);

create index category
    on products (category);

create table products_trail
(
    id           bigint auto_increment
        primary key,
    product_id   bigint       not null,
    type         varchar(255) not null,
    description  varchar(255) null,
    flag         varchar(255) not null,
    quantity     int          not null,
    stock_before int          not null,
    stock_after  int          not null,
    shop_id      int          not null,
    created_by   int          not null,
    created_on   timestamp    null,
    updated_on   timestamp    null
);

create index FKnp5bso3opxtnt6h77v0ouimg0
    on products_trail (product_id);

create index FKqu5rkpqnlxv30jfd8njyi2o2
    on products_trail (created_by);

create table promotions
(
    id          bigint auto_increment
        primary key,
    created_on  datetime     null,
    created_by  int          null,
    expiry_date varchar(255) null,
    promo_code  varchar(255) null,
    promo_days  int          null,
    status      varchar(255) null,
    type        varchar(255) null,
    updated_on  datetime     null,
    constraint UK_lb6axxr9obo82kwyavxvj917y
        unique (promo_code)
);

create table promotions_shop_entries
(
    id               bigint auto_increment
        primary key,
    created_by       int          null,
    created_on       datetime     null,
    is_redeemed      tinyint      null,
    payment_entry_id bigint       null,
    promo_days       int          null,
    promotion_id     bigint       null,
    redeemed_by      int          null,
    shop_id          bigint       null,
    status           varchar(255) null,
    updated_on       datetime     null
);

create table role
(
    role_id int auto_increment
        primary key,
    role    varchar(255) null
)
    charset = utf8mb3;

create table role_privieleges
(
    id           bigint auto_increment
        primary key,
    created_by   int      null,
    created_on   datetime null,
    privilege_id bigint   null,
    role_id      int      null,
    updated_on   datetime null
);

create table roles
(
    id   int          not null
        primary key,
    name varchar(255) not null,
    constraint UK_ofx66keruapi6vyqpv6f2or37
        unique (name)
);

create table sales
(
    id                   int auto_increment
        primary key,
    product              int                                        not null,
    buying_price         decimal(18, 2)                             not null,
    min_selling_price    decimal(18, 2)                             not null,
    selling_price        decimal(18, 2) default 0.00                not null,
    sold_at              decimal(18, 2)                             not null,
    quantity             int            default 0                   not null,
    flag                 varchar(50)    default '1'                 not null,
    profit_per_product   decimal(18, 2)                             not null,
    total_profit         decimal(18, 2)                             not null,
    total_amount         decimal(18, 2) default 0.00                not null,
    created_by           int                                        not null,
    payment_mode         varchar(50)                                null,
    created_on           timestamp      default current_timestamp() null on update current_timestamp(),
    updated_on           timestamp      default current_timestamp() null,
    shop_id              bigint                                     null,
    sales_transaction_id int                                        not null,
    constraint sales_ibfk_1
        foreign key (product) references products (id)
);

create index product
    on sales (product);

create table sales_transaction_payments
(
    id                   int auto_increment
        primary key,
    payment_method       int                                        not null,
    sales_transaction_id int                                        not null,
    flag                 varchar(45)    default '1'                 not null,
    amount               decimal(18, 2)                             not null,
    created_by           int                                        not null,
    payment_reference_no varchar(255)                               null,
    created_on           timestamp      default current_timestamp() null on update current_timestamp(),
    shop_id              int                                        not null,
    cash_given           decimal(18, 2) default 0.00                not null,
    cash_change          decimal(18, 2) default 0.00                not null
);

create table sales_transactions
(
    id           int auto_increment
        primary key,
    created_by   int                                        not null,
    shop_id      int                                        not null,
    status       varchar(50)                                not null,
    reference_no varchar(255)                               not null,
    customer_id  int                                        null,
    total_amount decimal(18, 2) default 0.00                not null,
    voided_by    int                                        null,
    void_reason  text                                       null,
    voided_on    timestamp                                  null,
    created_on   timestamp      default current_timestamp() not null,
    constraint reference_no_UNIQUE
        unique (reference_no)
);

create table shop_categories
(
    id          int auto_increment
        primary key,
    category    varchar(45)                             null,
    flag        varchar(45) default '1'                 null,
    description varchar(45)                             null,
    created_by  int                                     null,
    created_on  timestamp   default current_timestamp() null,
    updated_on  timestamp                               null,
    constraint id_UNIQUE
        unique (id)
);

create table shop_employee_groups
(
    id         bigint auto_increment
        primary key,
    created_by int          null,
    flag       varchar(255) null,
    role       varchar(255) null,
    shop_id    bigint       null,
    created_on timestamp    null
);

create index FK9l3coksjt0mwfjxphrn1588sx
    on shop_employee_groups (shop_id);

create table shop_employees
(
    id           bigint auto_increment
        primary key,
    active       varchar(51)                           null,
    role_id      bigint                                null,
    shop_id      bigint                                null,
    user_id      int                                   null,
    default_shop tinyint   default 0                   not null,
    created_by   int                                   null,
    created_on   timestamp default current_timestamp() null,
    updated_on   timestamp                             null,
    updated_by   int                                   null
);

create index FK4mprm6i4tjjh0bxr3hbjwan70
    on shop_employees (role_id);

create index FKs45bxh1ssof6vr9i29nhb8rcq
    on shop_employees (user_id);

create table shop_employees_group_rights
(
    id           bigint auto_increment
        primary key,
    created_by   int      null,
    created_on   datetime null,
    privilege_id bigint   null,
    role_id      bigint   null,
    shop_id      bigint   null,
    updated_on   datetime null
);

create index FK3se0qqp8oocy48mvwty06b7t5
    on shop_employees_group_rights (shop_id);

create table shop_package_payment_entries
(
    id              int auto_increment
        primary key,
    shop_id         int                                        not null,
    created_by      int                                        not null,
    reference_no    varchar(255)                               not null,
    plan_id         int                                        not null,
    status          varchar(255)                               not null,
    created_on      timestamp      default current_timestamp() not null,
    phone_number    varchar(45)                                null,
    updated_on      timestamp                                  null on update current_timestamp(),
    amount          decimal(18, 2) default 0.00                null,
    last_due        date                                       null,
    months_renewed  int            default 0                   null,
    days_before_due int            default 0                   null,
    days_added      int            default 0                   null,
    next_due_on     date                                       null,
    request_id      varchar(255)                               null,
    promo_code      varchar(255)                               null
);

create table shop_package_plans
(
    id            int auto_increment
        primary key,
    package_id    int                                   not null,
    plan          varchar(45)                           not null,
    duration_type varchar(45)                           not null,
    amount        decimal(18, 2)                        not null,
    duration      int                                   not null,
    flag          varchar(45)                           not null,
    created_on    timestamp default current_timestamp() not null
);

create table shop_payment_methods
(
    id                int auto_increment
        primary key,
    payment_method_id int                                     not null,
    shop_id           int                                     not null,
    active            tinyint     default 1                   not null,
    flag              varchar(45) default '1'                 not null,
    created_by        int                                     not null,
    created_on        timestamp   default current_timestamp() null on update current_timestamp()
);

create table shop_payment_packages
(
    id                  bigint auto_increment
        primary key,
    created_on          datetime      null,
    description         varchar(255)  null,
    flag                varchar(255)  null,
    is_free             tinyint       null,
    name                varchar(255)  null,
    updated_on          datetime      null,
    max_no_of_employees int default 0 null,
    color_code          varchar(45)   null,
    trial_days          varchar(45)   null,
    display_order       int default 1 null
);

create table shop_rights
(
    id            bigint auto_increment
        primary key,
    code          varchar(255)      null,
    created_by    int               null,
    created_on    datetime          null,
    name          varchar(255)      null,
    updated_on    datetime          null,
    default_right tinyint default 0 null
);

create table shops
(
    id              bigint auto_increment
        primary key,
    created_by      bigint                     null,
    flag            varchar(255)               null,
    location        varchar(255)               null,
    name            varchar(255)               null,
    phone           varchar(255)               null,
    plan_id         bigint                     null,
    latitude        bigint      default 0      null,
    longitude       bigint      default 0      null,
    created_on      timestamp                  null,
    updated_on      timestamp                  null,
    payment_status  varchar(45) default 'PAID' not null,
    trial           tinyint     default 1      not null,
    category_id     int                        null,
    marketer_id     int                        null,
    referral_code   varchar(100)               null,
    one_off_earning tinyint     default 0      null,
    payment_due_on  date                       null
);

create table suppliers
(
    id           int auto_increment
        primary key,
    name         varchar(50)                           not null,
    phone        varchar(20)                           null,
    email        varchar(50)                           null,
    address      varchar(100)                          null,
    description  varchar(100)                          null,
    created_on   timestamp default current_timestamp() null on update current_timestamp(),
    updated_on   timestamp default current_timestamp() null,
    bank         varchar(255)                          null,
    bank_account varchar(255)                          null,
    mpesa_phone  varchar(255)                          null,
    shop_id      bigint                                not null,
    created_by   int                                   not null
);

create table stock
(
    id                 int auto_increment
        primary key,
    product_id         int                                        not null,
    supplier_id        int                                        null,
    selling_price      decimal(18, 2) default 0.00                not null,
    min_selling_price  decimal(18, 2) default 0.00                not null,
    quantity           int                                        not null,
    last_stock_balance int            default 0                   not null,
    buying_price       decimal(18, 2)                             not null,
    shop_id            bigint                                     not null,
    created_by         int                                        not null,
    invoice_id         int                                        null,
    total_cost         decimal(18, 2)                             not null,
    created_on         timestamp      default current_timestamp() null on update current_timestamp(),
    updated_on         timestamp      default current_timestamp() null,
    constraint stock_ibfk_1
        foreign key (product_id) references products (id),
    constraint stock_ibfk_2
        foreign key (supplier_id) references suppliers (id)
);

create index product_id
    on stock (product_id);

create index supplier_id
    on stock (supplier_id);

create table transaction_types
(
    id         int auto_increment
        primary key,
    name       varchar(50)                             not null,
    code       varchar(50)                             not null,
    flag       varchar(45) default '1'                 null,
    enabled    tinyint     default 1                   null,
    created_on timestamp   default current_timestamp() null,
    updated_on timestamp                               null
);

create table transactions
(
    id                  int auto_increment
        primary key,
    reference_no        varchar(100)                               not null,
    created_by          int                                        not null,
    amount              decimal(18, 2) default 0.00                not null,
    transaction_type_no int                                        not null,
    flag                varchar(45)                                not null,
    status              varchar(45)                                not null,
    narration           text                                       null,
    updated_by          int                                        null,
    created_on          timestamp      default current_timestamp() not null,
    updated_on          timestamp                                  null
);

create index transction_type
    on transactions (transaction_type_no);

create index user
    on transactions (created_by);

create table user
(
    id              int auto_increment,
    firstname       varchar(255)             null,
    lastname        varchar(255)             null,
    email           varchar(255)             null,
    password        varchar(255)             not null,
    active          int                      null,
    phone           varchar(255) default '1' not null,
    locked          tinyint      default 0   null,
    user_type       int                      null,
    fire_base_token varchar(255)             null,
    last_login      timestamp                null,
    ip              varchar(45)              null,
    created_on      timestamp                null,
    app_version     varchar(45)              null,
    primary key (id, phone)
)
    charset = utf8mb3;

create table checkout
(
    id          int auto_increment
        primary key,
    created_by  int                                     not null,
    flag        varchar(50) default '1'                 not null,
    receipt_url varchar(255)                            null,
    customer_id int                                     null,
    created_on  timestamp   default current_timestamp() null on update current_timestamp(),
    updated_on  timestamp   default current_timestamp() null,
    shop_id     bigint                                  null,
    constraint FKfhr82l3th7cgtfx1rr0qjde6y
        foreign key (created_by) references user (id),
    constraint checkout_ibfk_2
        foreign key (customer_id) references customer (id)
);

create index customer_id
    on checkout (customer_id);

create table checkout_list
(
    id            int auto_increment
        primary key,
    product_id    int                                        not null,
    quantity      int            default 0                   not null,
    selling_price decimal(18, 2) default 0.00                not null,
    total         decimal(18, 2) default 0.00                not null,
    flag          varchar(50)    default '1'                 not null,
    created_by    int                                        not null,
    check_out_id  int                                        not null,
    created_on    timestamp      default current_timestamp() null on update current_timestamp(),
    updated_on    timestamp      default current_timestamp() null,
    shop_id       bigint                                     null,
    constraint checkout_list_ibfk_1
        foreign key (product_id) references products (id),
    constraint checkout_list_ibfk_2
        foreign key (created_by) references user (id)
);

create index checkout_list_ibfk_2_idx
    on checkout_list (created_by);

create index product_id
    on checkout_list (product_id);

create table marketers
(
    id                  int auto_increment
        primary key,
    user_id             int                                        not null,
    flag                varchar(45)                                null,
    status              varchar(45)                                not null,
    referral_code       varchar(45)                                null,
    percent_rate        decimal(18, 2) default 0.00                not null,
    wallet_id           int                                        null,
    created_by          int                                        null,
    updated_by          int                                        null,
    created_on          timestamp      default current_timestamp() not null,
    updated_on          timestamp                                  null,
    one_off_earning     tinyint        default 0                   null,
    firebase_token      varchar(255)                               null,
    app_version         varchar(45)                                null,
    last_login          timestamp                                  null,
    marketer_type       varchar(45)                                null,
    lead_by_marketer_id int                                        null,
    earn_from_my_team   tinyint        default 0                   null,
    leader_percent      decimal(18, 2) default 0.00                null,
    ip                  varchar(45)                                null,
    constraint FKabcc3fhjduy9156m5xii23pm1
        foreign key (user_id) references user (id)
);

create table user_role
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    constraint role_userrole
        foreign key (role_id) references role (role_id),
    constraint user_userrole
        foreign key (user_id) references user (id)
)
    charset = utf8mb3;

create index user_role_key
    on user_role (role_id);

create table user_type
(
    id          int auto_increment
        primary key,
    name        varchar(45)                           not null,
    description varchar(45)                           null,
    created_on  timestamp default current_timestamp() not null on update current_timestamp()
);

create table user_verify
(
    id         bigint auto_increment
        primary key,
    active     int          null,
    code       varchar(255) null,
    created_on datetime     null,
    updated_on datetime     null,
    username   varchar(255) null
);

create table users
(
    id         int auto_increment
        primary key,
    username   varchar(20)                           not null,
    phone      varchar(20)                           not null,
    password   varchar(255)                          not null,
    role       int                                   not null,
    created_on timestamp default current_timestamp() not null on update current_timestamp(),
    updated_on timestamp default current_timestamp() not null,
    email      varchar(255)                          not null,
    name       varchar(255)                          not null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint phone
        unique (phone),
    constraint username
        unique (username)
);

create table wallet_entries
(
    id                  int auto_increment
        primary key,
    wallet_id           int                                        not null,
    amount              decimal(18, 2) default 0.00                not null,
    balance             decimal(18, 2) default 0.00                not null,
    reference_no        varchar(100)                               not null,
    transaction_id      int                                        not null,
    narration           varchar(255)                               null,
    flag                varchar(45)    default '1'                 null,
    created_by          int                                        null,
    updated_by          int                                        null,
    created_on          timestamp      default current_timestamp() null,
    updated_on          timestamp                                  null,
    transaction_type_no int                                        null
);

create table wallet_types
(
    id         int auto_increment
        primary key,
    name       varchar(45)                           not null,
    code       varchar(45)                           not null,
    flag       varchar(45)                           not null,
    created_by int                                   null,
    updated_by int                                   null,
    created_on timestamp default current_timestamp() not null,
    updated_on timestamp                             null
);

create table wallets
(
    id             int auto_increment
        primary key,
    wallet_type_no int                                     not null,
    currency_no    int                                     null,
    flag           varchar(45) default '1'                 not null,
    created_by     int                                     null,
    updated_by     int                                     null,
    created_on     timestamp   default current_timestamp() null,
    updated_on     timestamp                               null
);


SET foreign_key_checks = 1;
