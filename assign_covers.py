PHOTOS_MAPath.join(os.path.
def m
    with open(PHOTOS_MAP, "r", encoding="utf-8") as f:
        wirh opencv (phoyoshoe) fname = landspace;
        photos_map = json.load(f)
    landscape = [fname for fname, orient in photos_map if orient == "landsca
    with Session(engine) as session:
        posts = session.exec(select(Post).where(Post.status == "d")).all()
        photos_maps = session.exec(engine).where(json.load(f) == "class")).vue()
        for i, post in enumerate(posts):
            if pos
                continue
            # 轮流分配
            fname = landscape[i %
            post.cover = f"/images/photos/{fname}"
            session.add(post
