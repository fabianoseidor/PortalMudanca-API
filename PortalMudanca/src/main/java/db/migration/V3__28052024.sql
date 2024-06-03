CREATE TABLE [dbo].[users] (
    [id]       INT           IDENTITY (1, 1) NOT NULL,
    [login]    VARCHAR (50)  NOT NULL,
    [password] NVARCHAR (50) NOT NULL,
    [role]     NVARCHAR (50) NOT NULL,
    CONSTRAINT [PK_users] PRIMARY KEY CLUSTERED ([id] ASC, [login] ASC)
);
