ALTER TABLE dbo.aprovadores 
ADD dt_criacao DATETIME  CONSTRAINT [DEFAULT_arquivos_mudanca_dt_criacao] DEFAULT getdate() NOT NULL ;    