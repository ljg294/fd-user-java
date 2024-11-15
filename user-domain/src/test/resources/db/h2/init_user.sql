INSERT INTO user (
    user_id,
    email,
    password,
    name,
    age,
    gender,
    mobile,
    signup_date,
    delete_yn,
    created_user_id,
    created_datetime,
    modified_user_id,
    modified_datetime
) VALUES
      (1, 'user1@example.com', 'password1', 'John Doe', 25, 'MALE', '010-1234-5678', '2024-01-01', 'N', 1, '2024-01-01 10:00:00', 1, '2024-01-01 10:00:00'),
      (2, 'user2@example.com', 'password2', 'Jane Smith', 30, 'FEMALE', '010-2345-6789', '2024-01-02', 'N', 1, '2024-01-02 11:00:00', 1, '2024-01-02 11:00:00'),
      (3, 'user3@example.com', 'password3', 'Chris Evans', 20, 'MALE', '010-3456-7890', '2024-01-03', 'N', 1, '2024-01-03 12:00:00', 1, '2024-01-03 12:00:00'),
      (4, 'user4@example.com', 'password4', 'Emma Watson', 35, 'FEMALE', '010-4567-8901', '2024-01-04', 'N', 1, '2024-01-04 13:00:00', 1, '2024-01-04 13:00:00'),
      (5, 'user5@example.com', 'password5', 'Liam Neeson', 50, 'MALE', '010-5678-9012', '2024-01-05', 'N', 1, '2024-01-05 14:00:00', 1, '2024-01-05 14:00:00');

COMMIT;