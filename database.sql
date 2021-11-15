PGDMP     -    2            
    y         
   reseau2tp2    13.4    13.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    32776 
   reseau2tp2    DATABASE     _   CREATE DATABASE reseau2tp2 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';
    DROP DATABASE reseau2tp2;
                philippebaillargeon    false            �            1259    32948    message    TABLE     �   CREATE TABLE public.message (
    id smallint NOT NULL,
    body text NOT NULL,
    sender_id smallint NOT NULL,
    receiver_id smallint[] NOT NULL
);
    DROP TABLE public.message;
       public         heap    philippebaillargeon    false            �            1259    32946    message_id_seq    SEQUENCE     �   ALTER TABLE public.message ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          philippebaillargeon    false    204            �            1259    32933 	   user_conv    TABLE     b   CREATE TABLE public.user_conv (
    user_id smallint NOT NULL,
    list_id smallint[] NOT NULL
);
    DROP TABLE public.user_conv;
       public         heap    philippebaillargeon    false            �            1259    32881    users    TABLE     �   CREATE TABLE public.users (
    id smallint NOT NULL,
    username character varying NOT NULL,
    address character varying NOT NULL
);
    DROP TABLE public.users;
       public         heap    philippebaillargeon    false            �            1259    32879    users_id_seq    SEQUENCE     �   ALTER TABLE public.users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          philippebaillargeon    false    201            �          0    32948    message 
   TABLE DATA           C   COPY public.message (id, body, sender_id, receiver_id) FROM stdin;
    public          philippebaillargeon    false    204          �          0    32933 	   user_conv 
   TABLE DATA           5   COPY public.user_conv (user_id, list_id) FROM stdin;
    public          philippebaillargeon    false    202   �       �          0    32881    users 
   TABLE DATA           6   COPY public.users (id, username, address) FROM stdin;
    public          philippebaillargeon    false    201          �           0    0    message_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.message_id_seq', 45, true);
          public          philippebaillargeon    false    203            �           0    0    users_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.users_id_seq', 40, true);
          public          philippebaillargeon    false    200            =           2606    32955    message message_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.message DROP CONSTRAINT message_pkey;
       public            philippebaillargeon    false    204            ;           2606    32940    user_conv user_conv_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.user_conv
    ADD CONSTRAINT user_conv_pkey PRIMARY KEY (user_id, list_id);
 B   ALTER TABLE ONLY public.user_conv DROP CONSTRAINT user_conv_pkey;
       public            philippebaillargeon    false    202    202            9           2606    32888    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            philippebaillargeon    false    201            ?           2606    32956    message sender_id    FK CONSTRAINT     r   ALTER TABLE ONLY public.message
    ADD CONSTRAINT sender_id FOREIGN KEY (sender_id) REFERENCES public.users(id);
 ;   ALTER TABLE ONLY public.message DROP CONSTRAINT sender_id;
       public          philippebaillargeon    false    3129    204    201            >           2606    32941    user_conv user_id    FK CONSTRAINT     p   ALTER TABLE ONLY public.user_conv
    ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES public.users(id);
 ;   ALTER TABLE ONLY public.user_conv DROP CONSTRAINT user_id;
       public          philippebaillargeon    false    3129    201    202            �   ~  x�u�Qn�0@��)����m(p�}iE����i�y�����[)��<�$l0�>N��`�2�T9�8�k��+,C0��Z���4g�Js�I�rT'T�1c�c�����L��<։m��j�0�F1J�ULdx��ϛC1e	����D��(P>(>��P�@��2��RF(F��P�P��2B1BeDb�ʈ���*#"#E�2BG-����w}���>W�J�>Fw��TA����ŕ�k���)������T�h���;��.���l���KG��_�y:>T���s�J ��>��5���5G/8�c�����~�&����O�����/V��w�G�6T/��R~�a�S�&��Z�����#�      �   d   x�M��� C��,�gEPv�&=w/���{!�@|�$H܁Đ�2��r#��X7�@5��tbx�1�w��,�X��I����f/U�V�<����~كa+�� >#�-n      �   �   x�U�K�0е}�?���,l
*����eE�ݼ���^/�3K����9T�"���E�]�hmbPrٿu��4�(�m�-��G�u���5�q�� �ቡ|��\sSBG�7|jd^�2!�Y1�     