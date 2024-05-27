USE [master]
GO
/****** Object:  Database [PORTALRDM_DEV]    Script Date: 25/05/2024 21:03:54 ******/
CREATE DATABASE [PORTALRDM_PRD]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'PORTALRDM_PRD', FILENAME = N'D:\Program Files\Microsoft SQL Server\MSSQL15.PORTALMULTICLOUD\MSSQL\DATA\PORTALRDM_PRD.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'PORTALRDM_PRD_log', FILENAME = N'D:\Program Files\Microsoft SQL Server\MSSQL15.PORTALMULTICLOUD\MSSQL\DATA\PORTALRDM_PRD_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [PORTALRDM_PRD] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [PORTALRDM_PRD].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [PORTALRDM_PRD] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET ARITHABORT OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [PORTALRDM_PRD] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [PORTALRDM_PRD] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET  DISABLE_BROKER 
GO
ALTER DATABASE [PORTALRDM_PRD] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [PORTALRDM_PRD] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET RECOVERY FULL 
GO
ALTER DATABASE [PORTALRDM_PRD] SET  MULTI_USER 
GO
ALTER DATABASE [PORTALRDM_PRD] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [PORTALRDM_PRD] SET DB_CHAINING OFF 
GO
ALTER DATABASE [PORTALRDM_PRD] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [PORTALRDM_PRD] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [PORTALRDM_PRD] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [PORTALRDM_PRD] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [PORTALRDM_PRD] SET QUERY_STORE = OFF
GO
ALTER AUTHORIZATION ON DATABASE::[PORTALRDM_PRD] TO [sa]
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  User [portalmulticloud]    Script Date: 25/05/2024 21:03:54 ******/
CREATE USER [portalmulticloud] FOR LOGIN [portalmulticloud] WITH DEFAULT_SCHEMA=[dbo]
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_aprovadores]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_aprovadores] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_aprovadores] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_arq_aprov_cliente]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_arq_aprov_cliente] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_arq_aprov_cliente] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_arquivos_mudanca]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_arquivos_mudanca] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_arquivos_mudanca] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_atividade_mudanca]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_atividade_mudanca] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_atividade_mudanca] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_categoria_padrao]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_categoria_padrao] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_categoria_padrao] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_clientes_afetados]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_clientes_afetados] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_clientes_afetados] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_criticidade]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_criticidade] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_criticidade] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_dados_mudanca]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_dados_mudanca] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_dados_mudanca] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_impacto_mudanca]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_impacto_mudanca] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_impacto_mudanca] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_mudanca]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_mudanca] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_mudanca] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_plano_comunicacao]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_plano_comunicacao] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_plano_comunicacao] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_report_final]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_report_final] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_report_final] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_responsavel_atividade]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_responsavel_atividade] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_responsavel_atividade] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_status_mudanca]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_status_mudanca] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_status_mudanca] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_tipo_arq]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_tipo_arq] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_tipo_arq] TO  SCHEMA OWNER 
GO
USE [PORTALRDM_PRD]
GO
/****** Object:  Sequence [dbo].[seq_tipo_mudanca]    Script Date: 25/05/2024 21:03:54 ******/
CREATE SEQUENCE [dbo].[seq_tipo_mudanca] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
ALTER AUTHORIZATION ON [dbo].[seq_tipo_mudanca] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[aprovadores]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[aprovadores](
	[id_aprovadores] [bigint] NOT NULL,
	[aprovador] [varchar](200) NOT NULL,
	[obs] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_aprovadores] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[aprovadores] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[arq_aprovacao_cliente]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[arq_aprovacao_cliente](
	[id_arq_apro_cli] [bigint] NOT NULL,
	[id_mudanca] [bigint] NULL,
	[id_tipo_arq] [bigint] NOT NULL,
	[arquivo] [varchar](255) NOT NULL,
	[dt_criacao] [datetime] NOT NULL,
	[nome_arq] [varchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_arq_apro_cli] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[arq_aprovacao_cliente] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[arquivos_mudanca]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[arquivos_mudanca](
	[id_arquivos_mudanca] [bigint] NOT NULL,
	[id_mudanca] [bigint] NULL,
	[id_tipo_arq] [bigint] NOT NULL,
	[dt_criacao] [datetime] NOT NULL,
	[nome_arq] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_arquivos_mudanca] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[arquivos_mudanca] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[atividade_mudanca]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[atividade_mudanca](
	[id_atividade_mudanca] [bigint] NOT NULL,
	[id_mudanca] [bigint] NULL,
	[id_responsavel_atividade] [bigint] NOT NULL,
	[atividade_mudanca] [nvarchar](max) NOT NULL,
	[duracao] [time](7) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_atividade_mudanca] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[atividade_mudanca] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[categoria_padrao]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[categoria_padrao](
	[id_categoria_padrao] [bigint] NOT NULL,
	[categoria_padrao] [varchar](200) NOT NULL,
	[dt_criacao] [datetime] NOT NULL,
	[obs] [varchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_categoria_padrao] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[categoria_padrao] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[clientes_afetados]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[clientes_afetados](
	[id_clientes_af] [bigint] NOT NULL,
	[id_cliente_portal] [bigint] NULL,
	[dt_criacao] [datetime] NOT NULL,
	[nome_cliente] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_clientes_af] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[clientes_afetados] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[criticidade]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[criticidade](
	[id_criticidade] [bigint] NOT NULL,
	[criticidade] [varchar](200) NOT NULL,
	[dt_criacao] [datetime] NOT NULL,
	[obs] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_criticidade] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[criticidade] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[dados_mudanca]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dados_mudanca](
	[id_dados_mudanca] [bigint] NOT NULL,
	[id_mudanca] [bigint] NOT NULL,
	[dsc_mudanca] [nvarchar](max) NULL,
	[dt_final] [date] NULL,
	[dt_inicio] [date] NULL,
	[hr_final] [time](7) NULL,
	[hr_inicio] [time](7) NULL,
	[justificativa_mudanca] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_dados_mudanca] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[dados_mudanca] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[impacto_mudanca]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[impacto_mudanca](
	[id_impacto_mudanca] [bigint] NOT NULL,
	[dt_criacao] [datetime] NOT NULL,
	[impacto_mudanca] [varchar](200) NOT NULL,
	[obs] [varchar](5000) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_impacto_mudanca] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[impacto_mudanca] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[lista_aprovadores]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lista_aprovadores](
	[id_mudanca] [bigint] NOT NULL,
	[id_aprovadores] [bigint] NOT NULL,
	[dt_criacao] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_aprovadores] ASC,
	[id_mudanca] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[lista_aprovadores] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[mudanca]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mudanca](
	[id_mudanca] [bigint] NOT NULL,
	[id_categoria_padrao] [bigint] NULL,
	[id_criticidade] [bigint] NOT NULL,
	[id_impacto_mudanca] [bigint] NOT NULL,
	[id_tipo_mudanca] [bigint] NOT NULL,
	[dt_criacao] [datetime] NOT NULL,
	[dt_alteracao] [datetime] NULL,
	[login_user] [varchar](100) NOT NULL,
	[status_mudanca] [varchar](255) NULL,
	[titulo_mudanca] [varchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_mudanca] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[mudanca] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[mudanca_clientes_afetados]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mudanca_clientes_afetados](
	[id_clientes_af] [bigint] NOT NULL,
	[id_mudanca] [bigint] NOT NULL,
	[dt_criacao] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_clientes_af] ASC,
	[id_mudanca] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[mudanca_clientes_afetados] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[plano_comunicacao]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[plano_comunicacao](
	[id_plano_comunicacao] [bigint] NOT NULL,
	[id_mudanca] [bigint] NOT NULL,
	[dt_criacao] [datetime] NOT NULL,
	[email] [varchar](200) NOT NULL,
	[empresa] [varchar](200) NOT NULL,
	[nome_contato] [varchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_plano_comunicacao] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[plano_comunicacao] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[report_final]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[report_final](
	[id_report_final] [bigint] NOT NULL,
	[id_mudanca] [bigint] NULL,
	[dt_criacao] [datetime] NOT NULL,
	[report_final] [nvarchar](max) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_report_final] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[report_final] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[responsavel_atividade]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[responsavel_atividade](
	[id_responsavel_atividade] [bigint] NOT NULL,
	[responsavel_atividade] [varchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_responsavel_atividade] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[responsavel_atividade] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[tipo_arq]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipo_arq](
	[id_tipo_arq] [bigint] NOT NULL,
	[tipo_arq] [varchar](4) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_tipo_arq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[tipo_arq] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[tipo_mudanca]    Script Date: 25/05/2024 21:03:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipo_mudanca](
	[id_tipo_mudanca] [bigint] NOT NULL,
	[tipo_mudanca] [varchar](255) NOT NULL,
	[dt_criacao] [datetime] NOT NULL,
	[obs] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_tipo_mudanca] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER AUTHORIZATION ON [dbo].[tipo_mudanca] TO  SCHEMA OWNER 
GO
ALTER TABLE [dbo].[arq_aprovacao_cliente]  WITH CHECK ADD  CONSTRAINT [fk_ARQ_APROVACAO_CLIENTE_MUDANCA] FOREIGN KEY([id_mudanca])
REFERENCES [dbo].[mudanca] ([id_mudanca])
GO
ALTER TABLE [dbo].[arq_aprovacao_cliente] CHECK CONSTRAINT [fk_ARQ_APROVACAO_CLIENTE_MUDANCA]
GO
ALTER TABLE [dbo].[arq_aprovacao_cliente]  WITH CHECK ADD  CONSTRAINT [fk_ARQ_APROVACAO_CLIENTE_TIPO_ARQ] FOREIGN KEY([id_tipo_arq])
REFERENCES [dbo].[tipo_arq] ([id_tipo_arq])
GO
ALTER TABLE [dbo].[arq_aprovacao_cliente] CHECK CONSTRAINT [fk_ARQ_APROVACAO_CLIENTE_TIPO_ARQ]
GO
ALTER TABLE [dbo].[arquivos_mudanca]  WITH CHECK ADD  CONSTRAINT [fk_ARQUIVOS_MUDANCA_MUD] FOREIGN KEY([id_mudanca])
REFERENCES [dbo].[mudanca] ([id_mudanca])
GO
ALTER TABLE [dbo].[arquivos_mudanca] CHECK CONSTRAINT [fk_ARQUIVOS_MUDANCA_MUD]
GO
ALTER TABLE [dbo].[arquivos_mudanca]  WITH CHECK ADD  CONSTRAINT [fk_ARQUIVOS_MUDANCA_TIPO_ARQ] FOREIGN KEY([id_tipo_arq])
REFERENCES [dbo].[tipo_arq] ([id_tipo_arq])
GO
ALTER TABLE [dbo].[arquivos_mudanca] CHECK CONSTRAINT [fk_ARQUIVOS_MUDANCA_TIPO_ARQ]
GO
ALTER TABLE [dbo].[atividade_mudanca]  WITH CHECK ADD  CONSTRAINT [fk_ATIV_MUD] FOREIGN KEY([id_mudanca])
REFERENCES [dbo].[mudanca] ([id_mudanca])
GO
ALTER TABLE [dbo].[atividade_mudanca] CHECK CONSTRAINT [fk_ATIV_MUD]
GO
ALTER TABLE [dbo].[atividade_mudanca]  WITH CHECK ADD  CONSTRAINT [fk_ATIV_MUD_RESP_ATIV] FOREIGN KEY([id_responsavel_atividade])
REFERENCES [dbo].[responsavel_atividade] ([id_responsavel_atividade])
GO
ALTER TABLE [dbo].[atividade_mudanca] CHECK CONSTRAINT [fk_ATIV_MUD_RESP_ATIV]
GO
ALTER TABLE [dbo].[dados_mudanca]  WITH CHECK ADD  CONSTRAINT [fk_DADOS_MUDANCA_MUD] FOREIGN KEY([id_mudanca])
REFERENCES [dbo].[mudanca] ([id_mudanca])
GO
ALTER TABLE [dbo].[dados_mudanca] CHECK CONSTRAINT [fk_DADOS_MUDANCA_MUD]
GO
ALTER TABLE [dbo].[lista_aprovadores]  WITH CHECK ADD  CONSTRAINT [fk_APROVADORES_MUDANCA] FOREIGN KEY([id_mudanca])
REFERENCES [dbo].[mudanca] ([id_mudanca])
GO
ALTER TABLE [dbo].[lista_aprovadores] CHECK CONSTRAINT [fk_APROVADORES_MUDANCA]
GO
ALTER TABLE [dbo].[lista_aprovadores]  WITH CHECK ADD  CONSTRAINT [fk_MUDA_APROV] FOREIGN KEY([id_aprovadores])
REFERENCES [dbo].[aprovadores] ([id_aprovadores])
GO
ALTER TABLE [dbo].[lista_aprovadores] CHECK CONSTRAINT [fk_MUDA_APROV]
GO
ALTER TABLE [dbo].[mudanca]  WITH CHECK ADD  CONSTRAINT [fk_MUDANCA_CATEGORIA_PADRAO] FOREIGN KEY([id_categoria_padrao])
REFERENCES [dbo].[categoria_padrao] ([id_categoria_padrao])
GO
ALTER TABLE [dbo].[mudanca] CHECK CONSTRAINT [fk_MUDANCA_CATEGORIA_PADRAO]
GO
ALTER TABLE [dbo].[mudanca]  WITH CHECK ADD  CONSTRAINT [fk_MUDANCA_CRITICIDADE] FOREIGN KEY([id_criticidade])
REFERENCES [dbo].[criticidade] ([id_criticidade])
GO
ALTER TABLE [dbo].[mudanca] CHECK CONSTRAINT [fk_MUDANCA_CRITICIDADE]
GO
ALTER TABLE [dbo].[mudanca]  WITH CHECK ADD  CONSTRAINT [fk_MUDANCA_IMPACTO_MUDANCA] FOREIGN KEY([id_impacto_mudanca])
REFERENCES [dbo].[impacto_mudanca] ([id_impacto_mudanca])
GO
ALTER TABLE [dbo].[mudanca] CHECK CONSTRAINT [fk_MUDANCA_IMPACTO_MUDANCA]
GO
ALTER TABLE [dbo].[mudanca]  WITH CHECK ADD  CONSTRAINT [fk_MUDANCA_TIPO_MUDANCA] FOREIGN KEY([id_tipo_mudanca])
REFERENCES [dbo].[tipo_mudanca] ([id_tipo_mudanca])
GO
ALTER TABLE [dbo].[mudanca] CHECK CONSTRAINT [fk_MUDANCA_TIPO_MUDANCA]
GO
ALTER TABLE [dbo].[mudanca_clientes_afetados]  WITH CHECK ADD  CONSTRAINT [fk_CLIE_AFET_MUD] FOREIGN KEY([id_mudanca])
REFERENCES [dbo].[mudanca] ([id_mudanca])
GO
ALTER TABLE [dbo].[mudanca_clientes_afetados] CHECK CONSTRAINT [fk_CLIE_AFET_MUD]
GO
ALTER TABLE [dbo].[mudanca_clientes_afetados]  WITH CHECK ADD  CONSTRAINT [fk_CLIENTES_AFETADOS] FOREIGN KEY([id_clientes_af])
REFERENCES [dbo].[clientes_afetados] ([id_clientes_af])
GO
ALTER TABLE [dbo].[mudanca_clientes_afetados] CHECK CONSTRAINT [fk_CLIENTES_AFETADOS]
GO
ALTER TABLE [dbo].[plano_comunicacao]  WITH CHECK ADD  CONSTRAINT [fk_PLANO_COMUNICACAO_MUDANCA] FOREIGN KEY([id_mudanca])
REFERENCES [dbo].[mudanca] ([id_mudanca])
GO
ALTER TABLE [dbo].[plano_comunicacao] CHECK CONSTRAINT [fk_PLANO_COMUNICACAO_MUDANCA]
GO
ALTER TABLE [dbo].[report_final]  WITH CHECK ADD  CONSTRAINT [fk_MUD_CAT_PAD] FOREIGN KEY([id_mudanca])
REFERENCES [dbo].[mudanca] ([id_mudanca])
GO
ALTER TABLE [dbo].[report_final] CHECK CONSTRAINT [fk_MUD_CAT_PAD]
GO
ALTER TABLE [dbo].[mudanca]  WITH CHECK ADD CHECK  (([status_mudanca]='MUDANCA_ABORTADA_ROLLBACK' OR [status_mudanca]='MUDANCA_ABORTADA' OR [status_mudanca]='MUDANCA_FIM_FALHA' OR [status_mudanca]='MUDANCA_FIM_RESSALVA' OR [status_mudanca]='MUDANCA_FIM_SUCESSO' OR [status_mudanca]='EM_EXECUCAO' OR [status_mudanca]='AGUARDANDO_EXECUCAO' OR [status_mudanca]='REJEITADA' OR [status_mudanca]='CANCELADA' OR [status_mudanca]='APROVADA' OR [status_mudanca]='AGUARDANDO_APROVACOES' OR [status_mudanca]='ABERTA'))
GO
USE [master]
GO
ALTER DATABASE [PORTALRDM_PRD] SET  READ_WRITE 
GO
