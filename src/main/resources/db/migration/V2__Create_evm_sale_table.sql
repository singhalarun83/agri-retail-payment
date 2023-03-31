
CREATE TABLE evm_sale_request(
    id bigint not null AUTO_INCREMENT,
    merchant_id varchar(24) NOT NULL,
    operator_id varchar(24),
    pos_package_id varchar(99) NOT NULL,
    secure_device varchar(40),
    user_trace varchar(24),
    card_type varchar(24),
    tran_code varchar(40) NOT NULL,
    processor_token varchar(14),
    collect_data varchar(40),
    account_no varchar(24),
    invoice_no varchar(50) NOT NULL,
    ref_no varchar(50) NOT NULL,
    return_clear_exp_date varchar(5),
    amount_purchase decimal(9,2) NOT NULL,
    amount_payment_fee decimal(9,2),
    amount_gratuity decimal(9,2),
    amount_gratuity_suggestions varchar(100),
    amount_cashback varchar(8),
    amount_maximum_cashback decimal(9,2),
    amount_tax decimal(9,2),
    amount_surcharge_with_lookup decimal(9,2),
    lane_id int,
    duplicate varchar(24),
    sequence_no varchar(20),
    record_no varchar(256),
    frequency varchar(40),
    partial_auth varchar(20) NOT NULL,
    ok_amount varchar(24),
    tran_customer_code varchar(17),
    tran_device_id varchar(24),
    card_holder_id varchar(50),
    merchant_defined_data varchar(75),
    payment_fee_description varchar(99),

    created_on datetime NOT NULL,
    updated_on datetime,
    created_by VARCHAR(30) NOT NULL,
    updated_by VARCHAR(30),
    PRIMARY KEY(id)
);