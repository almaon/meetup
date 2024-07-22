create table administrators_view (
    register_date timestamp(6),
    id uuid not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    login varchar(255),
    primary key (id)
);
create table admin_meeting_group_proposals_view (
    status smallint check (
        status between 0 and 2
    ),
    decision_date timestamp(6),
    proposal_date timestamp(6),
    decider_id uuid,
    id uuid not null,
    proposal_user_id uuid,
    decision_reject_reason varchar(255),
    description varchar(255),
    location_city varchar(255),
    location_country_code varchar(255),
    name varchar(255),
    primary key (id)
);
create table attendee (
    gust_number integer not null,
    is_fee_paid boolean,
    id uuid not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    name varchar(255),
    primary key (id)
);
create table confirmation_links_view (
    id varchar(16) not null,
    user_registration_id uuid,
    login varchar(255),
    primary key (id)
);
create table meeting (
    guests_number integer not null,
    is_canceled boolean,
    term_end_date timestamp(6),
    term_start_date timestamp(6),
    id uuid not null,
    description varchar(255),
    meeting_location_address varchar(255),
    meeting_location_city varchar(255),
    meeting_location_postal_code varchar(255),
    title varchar(255),
    primary key (id)
);
create table meeting_attendees_view (id uuid not null, primary key (id));
create table meeting_attendees_view_attendees (
    attendees_id uuid not null unique,
    meeting_attendees_view_id uuid not null
);
create table meeting_commenting_configurations_view (
    is_commenting_enabled boolean,
    id uuid not null,
    meeting_commenting_configuration_id uuid,
    primary key (id)
);
create table meeting_comment_liker (
    id uuid not null,
    meeting_member_comment_like_id uuid,
    name varchar(255),
    primary key (id)
);
create table meeting_comment_likers_view (id uuid not null, primary key (id));
create table meeting_comment_likers_view_meeting_comment_likers (
    meeting_comment_likers_id uuid not null unique,
    meeting_comment_likers_view_id uuid not null
);
create table meeting_comments_view (
    likes_count integer not null,
    create_date timestamp(6),
    edit_date timestamp(6),
    author_id uuid,
    id uuid not null,
    in_reply_to_comment_id uuid,
    comment varchar(255),
    primary key (id)
);
create table meeting_details_view (
    attendees_count integer not null,
    attendees_limit integer not null,
    event_fee_value float(53) not null,
    guests_count integer not null,
    guests_limit integer not null,
    is_canceled boolean,
    rsvp_term_end_date timestamp(6),
    rsvp_term_start_date timestamp(6),
    term_end_date timestamp(6),
    term_start_date timestamp(6),
    id uuid not null,
    meeting_group_id uuid,
    description varchar(255),
    event_fee_currency varchar(255),
    meeting_group_name varchar(255),
    meeting_location_address varchar(255),
    meeting_location_city varchar(255),
    meeting_location_name varchar(255),
    meeting_location_postal_code varchar(255),
    title varchar(255),
    primary key (id)
);
create table meeting_details_view_hosts (
    hosts uuid,
    meeting_details_view_id uuid not null
);
create table meeting_fee_payment_status (
    is_payed boolean,
    payed_date timestamp(6),
    id uuid not null,
    primary key (id)
);
create table meeting_fees_view (
    fee_value float(53) not null,
    id uuid not null,
    meeting_id uuid,
    payer_id uuid,
    fee_currency varchar(255),
    status varchar(255),
    primary key (id)
);
create table meeting_group (
    member_count integer not null,
    joined_date timestamp(6),
    id uuid not null,
    meeting_group_id uuid,
    description varchar(255),
    location_city varchar(255),
    location_country_code varchar(255),
    member_role varchar(255),
    name varchar(255),
    primary key (id)
);
create table meeting_group_member (
    id uuid not null,
    member_id uuid,
    email varchar(255),
    name varchar(255),
    role varchar(255),
    primary key (id)
);
create table meeting_group_proposals_view (
    proposal_date timestamp(6),
    id uuid not null,
    proposal_user_id uuid,
    description varchar(255),
    location_city varchar(255),
    location_country_code varchar(255),
    name varchar(255),
    reject_reason varchar(255),
    status varchar(255),
    primary key (id)
);
create table meeting_groups_view (
    member_count integer not null,
    payment_date_to timestamp(6),
    id uuid not null,
    description varchar(255),
    location_city varchar(255),
    location_country_code varchar(255),
    name varchar(255),
    primary key (id)
);
create table meeting_groups_view_meeting_group_members (
    meeting_group_members_id uuid not null unique,
    meeting_groups_view_id uuid not null
);
create table meetings_meeting_fees_payment_status_view (id uuid not null, primary key (id));
create table meetings_meeting_fees_payment_status_view_meeting_fee_payment_status (
    meeting_fee_payment_status_id uuid not null unique,
    meetings_meeting_fees_payment_status_view_id uuid not null
);
create table meetings_view (
    is_canceled boolean,
    term_end_date timestamp(6),
    term_start_date timestamp(6),
    id uuid not null,
    meeting_group_id uuid,
    description varchar(255),
    meeting_group_name varchar(255),
    meeting_location_address varchar(255),
    meeting_location_city varchar(255),
    meeting_location_postal_code varchar(255),
    title varchar(255),
    primary key (id)
);
create table meeting_waitlists_view (id uuid not null, primary key (id));
create table meeting_waitlists_view_members_in_wait_list (
    meeting_waitlists_view_id uuid not null,
    members_in_wait_list_id uuid not null unique
);
create table meetup_user (
    user_id uuid not null,
    password varchar(255),
    username varchar(255),
    primary key (user_id)
);
create table meetup_user_authorities (
    meetup_user_user_id uuid not null,
    authorities varchar(255)
);
create table member_in_wait_list (
    is_moved_to_attendee boolean,
    is_signed_off boolean,
    position integer not null,
    moved_to_attendees_date timestamp(6),
    sign_off_date timestamp(6),
    sign_up_date timestamp(6),
    id uuid not null,
    name varchar(255),
    primary key (id)
);
create table member_meeting_groups_view (id uuid not null, primary key (id));
create table member_meeting_groups_view_meeting_groups (
    meeting_groups_id uuid not null unique,
    member_meeting_groups_view_id uuid not null
);
create table member_meetings_view (id uuid not null, primary key (id));
create table member_meetings_view_meetings (
    meetings_id uuid not null unique,
    member_meetings_view_id uuid not null
);
create table member_view (
    register_date timestamp(6),
    id uuid not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    login varchar(255),
    primary key (id)
);
create table payer_view (
    register_date timestamp(6),
    id uuid not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    login varchar(255),
    primary key (id)
);
create table price_list_items_view (
    is_active boolean,
    price_value float(53) not null,
    id uuid not null,
    category_code varchar(255),
    country_code varchar(255),
    price_currency varchar(255),
    subscription_period_code varchar(255),
    primary key (id)
);
create table subscription_payments_view (
    money_value float(53) not null,
    date timestamp(6),
    id uuid not null,
    payer_id uuid,
    subscription_id uuid,
    money_currency varchar(255),
    period varchar(255),
    status varchar(255),
    type varchar(255),
    primary key (id)
);
create table subscriptions_view (
    expiration_date timestamp(6),
    id uuid not null,
    period varchar(255),
    status varchar(255),
    primary key (id)
);
create table users_view (
    user_type smallint check (
        user_type between 0 and 1
    ),
    register_date timestamp(6),
    id uuid not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    login varchar(255),
    primary key (id)
);


create table administration_event (occured_on timestamp(6), id uuid not null, data varchar(2047), event_name varchar(255), primary key (id));
create table administration_event_stream (version integer not null, id varchar(100) not null, primary key (id));
create table administration_event_stream_events (events_id uuid not null unique, administration_event_stream_id varchar(100) not null);

alter table if exists administration_event_stream_events add constraint FK3q48d3fuvuruu6y20a5wd9aj0 foreign key (events_id) references administration_event;
alter table if exists administration_event_stream_events add constraint FK97ajeuvt6n9vvhos7gok68wmf foreign key (administration_event_stream_id) references administration_event_stream;

create table payments_event (occured_on timestamp(6), id uuid not null, data varchar(2047), event_name varchar(255), primary key (id));
create table payments_event_stream (id varchar(100) not null, primary key (id));
create table payments_event_stream_events (events_id uuid not null unique, payments_event_stream_id varchar(100) not null);

alter table if exists payments_event_stream_events add constraint FKg4e88jv0xuel9hf3betbi2o9k foreign key (events_id) references payments_event;
alter table if exists payments_event_stream_events add constraint FKbr13aswmtkp12nmsvw60efnvj foreign key (payments_event_stream_id) references payments_event_stream;

create table user_access_event (occured_on timestamp(6), id uuid not null, data varchar(2047), event_name varchar(255), primary key (id));
create table user_access_event_stream (id varchar(100) not null, primary key (id));
create table user_access_event_stream_events (events_id uuid not null unique, user_access_event_stream_id varchar(100) not null);

alter table if exists user_access_event_stream_events add constraint FK6c6ka25il6mf2piir6daysehc foreign key (events_id) references user_access_event;
alter table if exists user_access_event_stream_events add constraint FKtkb17l0e2m072g0yny3trvqht foreign key (user_access_event_stream_id) references user_access_event_stream;

create table meetings_event (occured_on timestamp(6), id uuid not null, data varchar(2047), event_name varchar(255), primary key (id));
create table meetings_event_stream (version integer not null, id varchar(100) not null, primary key (id));
create table meetings_event_stream_events (events_id uuid not null unique, meetings_event_stream_id varchar(100) not null);

alter table if exists meetings_event_stream_events add constraint FKio5vk2klc3dxgfcn0x4jk2wfn foreign key (events_id) references meetings_event;
alter table if exists meetings_event_stream_events add constraint FK9apjl3s9tvr33skbdh355m48r foreign key (meetings_event_stream_id) references meetings_event_stream;

alter table if exists meeting_attendees_view_attendees
add constraint FKfqm0kllpshcf7kasf7un3y4lg foreign key (attendees_id) references attendee;
alter table if exists meeting_attendees_view_attendees
add constraint FKqbfcpv5srue5jw7ivpv70enus foreign key (meeting_attendees_view_id) references meeting_attendees_view;
alter table if exists meeting_comment_likers_view_meeting_comment_likers
add constraint FKs9dbvc4i0dhd18qw8mqjyjcpp foreign key (meeting_comment_likers_id) references meeting_comment_liker;
alter table if exists meeting_comment_likers_view_meeting_comment_likers
add constraint FKtqd16ntbi626k2u7hdh40mu8g foreign key (meeting_comment_likers_view_id) references meeting_comment_likers_view;
alter table if exists meeting_details_view_hosts
add constraint FKiqe94p526ifwcnxjfe9i4h279 foreign key (meeting_details_view_id) references meeting_details_view;
alter table if exists meeting_groups_view_meeting_group_members
add constraint FKsjb565ti6ml7v68we2x4wxitu foreign key (meeting_group_members_id) references meeting_group_member;
alter table if exists meeting_groups_view_meeting_group_members
add constraint FK9chr91n9cc3b5270x0s4pb44l foreign key (meeting_groups_view_id) references meeting_groups_view;
alter table if exists meetings_meeting_fees_payment_status_view_meeting_fee_payment_status
add constraint FKd3kc9hdtwx121o5dsxw72xdbi foreign key (meeting_fee_payment_status_id) references meeting_fee_payment_status;
alter table if exists meetings_meeting_fees_payment_status_view_meeting_fee_payment_status
add constraint FKueh3titfr2034q1l1cj28ydb foreign key (meetings_meeting_fees_payment_status_view_id) references meetings_meeting_fees_payment_status_view;
alter table if exists meeting_waitlists_view_members_in_wait_list
add constraint FKc4hgl10q9h5piyu9g7uwkhs13 foreign key (members_in_wait_list_id) references member_in_wait_list;
alter table if exists meeting_waitlists_view_members_in_wait_list
add constraint FKjbnjg54e6ffdwinngwgp2s38m foreign key (meeting_waitlists_view_id) references meeting_waitlists_view;
alter table if exists meetup_user_authorities
add constraint FKbmpiaqgsy9nijwdmy93th6ken foreign key (meetup_user_user_id) references meetup_user;
alter table if exists member_meeting_groups_view_meeting_groups
add constraint FK4olhus15o0fgf13ctefwvu80h foreign key (meeting_groups_id) references meeting_group;
alter table if exists member_meeting_groups_view_meeting_groups
add constraint FKgxxt5jv8qmxy1sm93gplewxw9 foreign key (member_meeting_groups_view_id) references member_meeting_groups_view;
alter table if exists member_meetings_view_meetings
add constraint FK2pj3lok151v9yyxbyvtqosv68 foreign key (meetings_id) references meeting;
alter table if exists member_meetings_view_meetings
add constraint FKda62rntvwbfm6ja8dr94rgmag foreign key (member_meetings_view_id) references member_meetings_view;
