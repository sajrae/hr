--
-- PostgreSQL database dump
--

--
-- TOC entry 227 (class 1259 OID 17379)
-- Name: allowance_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.allowance_type
(
    taxable       boolean,
    creation_time timestamp(6) without time zone,
    id            bigint NOT NULL,
    modified_time timestamp(6) without time zone,
    created_by    character varying(255),
    name          character varying(255),
    updated_by    character varying(255)
);


CREATE TABLE public.attendance
(
    date           date,
    late_minutes   integer,
    overtime_hours double precision,
    time_in        time(6) without time zone,
    time_out       time(6) without time zone,
    under_time     boolean NOT NULL,
    worked_hours   double precision,
    creation_time  timestamp(6) without time zone,
    employee_id    bigint,
    id             bigint  NOT NULL,
    modified_time  timestamp(6) without time zone,
    created_by     character varying(255),
    updated_by     character varying(255)
);


--
-- TOC entry 231 (class 1259 OID 17398)
-- Name: attendance_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.attendance_log
(
    biometric_device_id bigint,
    creation_time       timestamp(6) without time zone,
    employee_id         bigint,
    id                  bigint NOT NULL,
    modified_time       timestamp(6) without time zone,
    "timestamp"         timestamp(6) without time zone,
    created_by          character varying(255),
    device_id           character varying(255),
    type                character varying(255),
    updated_by          character varying(255)
);


--
-- TOC entry 233 (class 1259 OID 17407)
-- Name: deduction_bracket; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.deduction_bracket
(
    contribution      double precision,
    max_salary        double precision,
    min_salary        double precision,
    deduction_type_id bigint,
    id                bigint NOT NULL
);

--
-- TOC entry 235 (class 1259 OID 17414)
-- Name: deduction_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.deduction_type
(
    mandatory boolean,
    rate      double precision,
    id        bigint NOT NULL,
    name      character varying(255)
);


--
-- TOC entry 237 (class 1259 OID 17421)
-- Name: department; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.department
(
    creation_time timestamp(6) without time zone,
    id            bigint                 NOT NULL,
    modified_time timestamp(6) without time zone,
    code          character varying(255) NOT NULL,
    created_by    character varying(255),
    description   character varying(255),
    name          character varying(255),
    updated_by    character varying(255)
);


--
-- TOC entry 238 (class 1259 OID 17432)
-- Name: device; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.device
(
    id         bigint NOT NULL,
    ip_address character varying(255),
    location   character varying(255),
    name       character varying(255)
);

--
-- TOC entry 240 (class 1259 OID 17441)
-- Name: employee; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employee
(
    creation_time            timestamp(6) without time zone,
    department_id            bigint,
    id                       bigint NOT NULL,
    modified_time            timestamp(6) without time zone,
    payroll_schedule_code_id bigint,
    position_id              bigint,
    created_by               character varying(255),
    email                    character varying(255),
    employee_code            character varying(255),
    first_name               character varying(255),
    hire_date                character varying(255),
    last_name                character varying(255),
    phone                    character varying(255),
    status                   character varying(255),
    updated_by               character varying(255)
);


--
-- TOC entry 241 (class 1259 OID 17449)
-- Name: employee_allowance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employee_allowance
(
    amount            double precision,
    allowance_type_id bigint,
    creation_time     timestamp(6) without time zone,
    employee_id       bigint,
    id                bigint NOT NULL,
    modified_time     timestamp(6) without time zone,
    created_by        character varying(255),
    updated_by        character varying(255)
);

--
-- TOC entry 243 (class 1259 OID 17458)
-- Name: employee_leave; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employee_leave
(
    leave_date    date,
    paid          boolean,
    creation_time timestamp(6) without time zone,
    employee_id   bigint,
    id            bigint NOT NULL,
    leave_type_id bigint,
    modified_time timestamp(6) without time zone,
    created_by    character varying(255),
    updated_by    character varying(255)
);


--
-- TOC entry 245 (class 1259 OID 17467)
-- Name: employee_salary; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employee_salary
(
    base_salary       numeric(38, 2),
    effective_date    date,
    creation_time     timestamp(6) without time zone,
    employee_id       bigint,
    id                bigint NOT NULL,
    modified_time     timestamp(6) without time zone,
    over_time_rule_id bigint,
    tax_rule_id       bigint,
    created_by        character varying(255),
    updated_by        character varying(255)
);


--
-- TOC entry 246 (class 1259 OID 17477)
-- Name: holiday; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.holiday
(
    date          date,
    multiplier    double precision,
    creation_time timestamp(6) without time zone,
    id            bigint NOT NULL,
    modified_time timestamp(6) without time zone,
    created_by    character varying(255),
    name          character varying(255),
    type          character varying(255),
    updated_by    character varying(255),
    CONSTRAINT holiday_type_check CHECK (((type)::text = ANY ((ARRAY['REGULAR':: character varying, 'SPECIAL':: character varying])::text[])
) )
);


--
-- TOC entry 247 (class 1259 OID 17486)
-- Name: leave_request; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.leave_request
(
    days          integer,
    end_date      date,
    start_date    date,
    creation_time timestamp(6) without time zone,
    employee_id   bigint,
    id            bigint NOT NULL,
    leave_type_id bigint,
    modified_time timestamp(6) without time zone,
    created_by    character varying(255),
    reason        character varying(255),
    status        character varying(255),
    updated_by    character varying(255),
    CONSTRAINT leave_request_status_check CHECK (((status)::text = ANY ((ARRAY['PENDING':: character varying, 'APPROVED':: character varying, 'REJECTED':: character varying])::text[])
) )
);

--
-- TOC entry 248 (class 1259 OID 17495)
-- Name: leave_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.leave_type
(
    is_paid           boolean,
    max_days_per_year integer,
    creation_time     timestamp(6) without time zone,
    id                bigint NOT NULL,
    modified_time     timestamp(6) without time zone,
    created_by        character varying(255),
    name              character varying(255),
    updated_by        character varying(255)
);


--
-- TOC entry 250 (class 1259 OID 17504)
-- Name: over_time_rule; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.over_time_rule
(
    multiplier    double precision,
    creation_time timestamp(6) without time zone,
    id            bigint NOT NULL,
    modified_time timestamp(6) without time zone,
    created_by    character varying(255),
    description   character varying(255),
    name          character varying(255),
    updated_by    character varying(255)
);


--
-- TOC entry 251 (class 1259 OID 17512)
-- Name: payroll; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.payroll
(
    basic_salary      numeric(38, 2),
    gross_salary      numeric(38, 2),
    holiday_pay       numeric(38, 2),
    late_deduction    numeric(38, 2),
    leave_deduction   numeric(38, 2),
    net_salary        numeric(38, 2),
    overtime_pay      numeric(38, 2),
    pagibig           numeric(38, 2),
    phil_health       numeric(38, 2),
    sss               numeric(38, 2),
    tax               numeric(38, 2),
    total_allowance   numeric(38, 2),
    creation_time     timestamp(6) without time zone,
    employee_id       bigint,
    id                bigint NOT NULL,
    modified_time     timestamp(6) without time zone,
    payroll_period_id bigint,
    created_by        character varying(255),
    updated_by        character varying(255)
);


--
-- TOC entry 253 (class 1259 OID 17521)
-- Name: payroll_period; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.payroll_period
(
    end_date      date,
    start_date    date,
    creation_time timestamp(6) without time zone,
    id            bigint NOT NULL,
    modified_time timestamp(6) without time zone,
    created_by    character varying(255),
    status        character varying(255),
    updated_by    character varying(255),
    CONSTRAINT payroll_period_status_check CHECK (((status)::text = ANY ((ARRAY['OPEN':: character varying, 'CLOSED':: character varying, 'PROCESSED':: character varying])::text[])
) )
);


--
-- TOC entry 255 (class 1259 OID 17531)
-- Name: payroll_schedule; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.payroll_schedule
(
    creation_time timestamp(6) without time zone,
    id            bigint NOT NULL,
    modified_time timestamp(6) without time zone,
    created_by    character varying(255),
    name          character varying(255),
    pay_days      character varying(255),
    payroll_type  character varying(255),
    updated_by    character varying(255),
    CONSTRAINT payroll_schedule_payroll_type_check CHECK (((payroll_type)::text = ANY ((ARRAY['WEEKLY':: character varying, 'SEMI_MONTHLY':: character varying, 'MONTHLY':: character varying, 'DAILY':: character varying])::text[])
) )
);

--
-- TOC entry 257 (class 1259 OID 17541)
-- Name: position; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."position"
(
    creation_time timestamp(6) without time zone,
    id            bigint NOT NULL,
    modified_time timestamp(6) without time zone,
    created_by    character varying(255),
    description   character varying(255),
    title         character varying(255),
    updated_by    character varying(255)
);


--
-- TOC entry 259 (class 1259 OID 17550)
-- Name: system_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.system_settings
(
    id            bigint NOT NULL,
    description   character varying(255),
    setting_key   character varying(255),
    setting_value character varying(255)
);


--
-- TOC entry 261 (class 1259 OID 17561)
-- Name: tax_rule; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tax_rule
(
    rate          double precision,
    creation_time timestamp(6) without time zone,
    id            bigint NOT NULL,
    modified_time timestamp(6) without time zone,
    created_by    character varying(255),
    description   character varying(255),
    name          character varying(255),
    updated_by    character varying(255)
);


