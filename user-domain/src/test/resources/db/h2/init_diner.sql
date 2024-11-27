INSERT INTO diner (
    diner_id,
    owner_member_id,
    diner_name,
    diner_address,
    delete_yn,
    created_member_id,
    created_date_time,
    modified_member_id,
    modified_date_time
) VALUES
      (1, 1, 'The Gourmet Spot', '123 Culinary Ave, Foodtown', 'N', 1, '2024-01-01 15:00:00', 1, '2024-01-01 15:00:00'),
      (2, 2, 'Seafood Paradise', '456 Ocean Drive, Seaville', 'N', 2, '2024-01-02 16:00:00', 2, '2024-01-02 16:00:00'),
      (3, 3, 'Mountain View Grill', '789 Alpine St, Hilltop', 'N', 3, '2024-01-03 17:00:00', 3, '2024-01-03 17:00:00'),
      (4, 4, 'Urban Eats', '321 City Square, Metro City', 'N', 4, '2024-01-04 18:00:00', 4, '2024-01-04 18:00:00'),
      (5, 5, 'Countryside Cafe', '654 Farm Lane, Greenfield', 'N', 5, '2024-01-05 19:00:00', 5, '2024-01-05 19:00:00');

COMMIT;